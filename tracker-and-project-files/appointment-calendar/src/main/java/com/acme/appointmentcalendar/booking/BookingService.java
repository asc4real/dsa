package com.acme.appointmentcalendar.booking;



import com.acme.appointmentcalendar.shared.*;
import com.acme.appointmentcalendar.security.*;
import com.acme.appointmentcalendar.catalog.*;
import com.acme.appointmentcalendar.availability.*;
import com.acme.appointmentcalendar.reservation.*;
import com.acme.appointmentcalendar.idempotency.*;
import com.acme.appointmentcalendar.audit.*;
import com.acme.appointmentcalendar.outbox.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import java.time.*;
import java.util.*;
import java.util.function.*;
@Service
public class BookingService {
 private final long ttlSeconds;private final Sql db;private final com.acme.appointmentcalendar.shared.CursorCodec cursors;private final Permissions auth;private final CatalogReader catalog;private final AvailabilityPolicy availability;
 private final ReservationPort reservations;private final CandidateResourceAllocator allocator;private final IdempotencyCoordinator idem;private final AuditWriter audit;private final OutboxWriter outbox;
 public BookingService(Sql db,Permissions auth,CatalogReader catalog,AvailabilityPolicy availability,
       ReservationPort reservations,CandidateResourceAllocator allocator,IdempotencyCoordinator idem,AuditWriter audit,OutboxWriter outbox, com.acme.appointmentcalendar.shared.CursorCodec cursors, @Value("${app.hold-ttl-seconds:300}") long ttlSeconds){
  this.db=db;this.cursors=cursors;this.auth=auth;this.catalog=catalog;this.availability=availability;
  this.reservations=reservations;this.allocator=allocator;this.idem=idem;this.audit=audit;this.outbox=outbox;this.ttlSeconds=ttlSeconds;
 }
 private UUID uuid(Map<String,Object> m,String key){
  Object v=m.get(key);if(v==null)throw ApiFailure.bad("Missing "+key);
  try{return UUID.fromString(v.toString());}catch(Exception ex){throw ApiFailure.bad("Invalid "+key);}
 }
 private UUID optionalUuid(Map<String,Object> m,String key){return m.get(key)==null?null:uuid(m,key);}
 private Instant instant(Map<String,Object> m,String key){
  try{return OffsetDateTime.parse(Objects.toString(m.get(key))).toInstant();}catch(Exception ex){throw ApiFailure.bad("Invalid "+key+"; use an offset-aware timestamp");}
 }
 private int n(Map<String,Object> s,String key){return ((Number)s.get(key)).intValue();}
 private Map<String,Object> hold(UUID org,UUID hold,boolean lock){
  Map<String,Object> h=db.one("SELECT * FROM booking_hold WHERE organization_id=? AND id=?"+(lock?" FOR UPDATE":""),org,hold);
  auth.ownerOrElevated(org,(UUID)h.get("customerUserProfileId"));return h;
 }
 private Map<String,Object> appointment(UUID org,UUID id,boolean lock){
  Map<String,Object> a=db.one("SELECT * FROM appointment WHERE organization_id=? AND id=?"+(lock?" FOR UPDATE":""),org,id);
  UUID customer=(UUID)a.get("customerUserProfileId");
  if(!auth.elevated(org)&&!customer.equals(auth.actor().userId())){
   if(!auth.staff(org)||a.get("staffId")==null || db.optional("SELECT id FROM staff_member WHERE organization_id=? AND id=? AND user_profile_id=?",org,a.get("staffId"),auth.actor().userId()).isEmpty())
    throw ApiFailure.notFound();
  }return a;
 }
 private Instant dbNow(){return (Instant)db.one("SELECT clock_timestamp() AS current_time").get("currentTime");}
 private void history(UUID org,UUID appointment,String before,String after,String reason){
  db.update("INSERT INTO appointment_status_history(id,organization_id,appointment_id,from_status,to_status,reason,actor_user_profile_id) VALUES(?,?,?,?,?,?,?)",
    UUID.randomUUID(),org,appointment,before,after,reason,auth.actor().userId());
 }
 private void events(UUID org,String event,UUID id){audit.record(org,auth.actor().userId(),event,"APPOINTMENT",id);outbox.record(org,event,id,Map.of("appointmentId",id.toString()));}
 private void policy(UUID org,UUID svc,UUID location,Instant start,List<Map<String,Object>> resources){availability.validate(org,svc,location,start,resources);}
 private Map<String,Object> viewHold(UUID org,UUID id){
  var h=db.one("SELECT h.*,l.zone_id AS time_zone FROM booking_hold h JOIN location l ON h.organization_id=l.organization_id AND h.location_id=l.id WHERE h.organization_id=? AND h.id=?",org,id);
  h.remove("customerUserProfileId");h.remove("staffId");h.remove("createdAt");h.remove("version");
  h.remove("serviceNameSnapshot");h.remove("durationMinutesSnapshot");
  h.remove("bufferBeforeMinutesSnapshot");h.remove("bufferAfterMinutesSnapshot");
  h.remove("cancellationDeadlineMinutesSnapshot");h.remove("rescheduleDeadlineMinutesSnapshot");return h;
 }
 private Map<String,Object> viewAppointment(UUID org,UUID id){
  var a=db.one("SELECT a.*,l.zone_id AS time_zone FROM appointment a JOIN location l ON a.organization_id=l.organization_id AND a.location_id=l.id WHERE a.organization_id=? AND a.id=?",org,id);
  a.remove("staffId");a.remove("bufferBeforeMinutesSnapshot");a.remove("bufferAfterMinutesSnapshot");a.remove("cancellationDeadlineMinutesSnapshot");a.remove("rescheduleDeadlineMinutesSnapshot");return a;
 }
 @Transactional
 public Map<String,Object> createHold(UUID org,String key,Map<String,Object> input){
  auth.member(org);Actor actor=auth.actor();return idem.execute(org,actor,"create-hold",key,input,()->{
   UUID service=uuid(input,"serviceId"),location=uuid(input,"locationId"),staff=optionalUuid(input,"preferredStaffId");Instant start=instant(input,"startAt");
   var s=catalog.service(org,service);List<Map<String,Object>> selected=allocator.allocate(org,service,location,start,staff);
   // Lock membership as quota mutex across pods; row-lock is held until commit.
   db.one("SELECT id FROM organization_membership WHERE organization_id=? AND user_profile_id=? AND status='ACTIVE' FOR UPDATE",org,actor.userId());
   long count=((Number)db.one("SELECT COUNT(*) AS n FROM booking_hold WHERE organization_id=? AND customer_user_profile_id=? AND status='ACTIVE' AND expires_at>clock_timestamp()",org,actor.userId()).get("n")).longValue();
   if(count>=3)throw new ApiFailure(429,"HOLD_LIMIT_REACHED","Maximum three active holds");
   int duration=n(s,"durationMinutes");Instant end=start.plusSeconds(duration*60L);UUID id=UUID.randomUUID();
   db.update("""
    INSERT INTO booking_hold(id,organization_id,service_id,location_id,customer_user_profile_id,staff_id,start_at,end_at,
      service_name_snapshot,duration_minutes_snapshot,buffer_before_minutes_snapshot,buffer_after_minutes_snapshot,
      cancellation_deadline_minutes_snapshot,reschedule_deadline_minutes_snapshot,expires_at)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,clock_timestamp() + (? * INTERVAL '1 second'))
    """,id,org,service,location,actor.userId(),staff,start,end,s.get("name"),s.get("durationMinutes"),s.get("bufferBeforeMinutes"),s.get("bufferAfterMinutes"),
        s.get("cancellationDeadlineMinutes"),s.get("rescheduleDeadlineMinutes"),ttlSeconds);
   reservations.create(org,id,null,selected,start,end,n(s,"bufferBeforeMinutes"),n(s,"bufferAfterMinutes"));
   audit.record(org,actor.userId(),"BOOKING_HOLD_CREATED","BOOKING_HOLD",id);
   return viewHold(org,id);
  });
 }
 @Transactional
 public Map<String,Object> getHold(UUID org,UUID id){hold(org,id,false);return viewHold(org,id);}
 @Transactional
 public Map<String,Object> cancelHold(UUID org,UUID id,String key){
  Actor actor=auth.actor();auth.member(org);hold(org,id,false);return idem.execute(org,actor,"cancel-hold:"+id,key,Map.of("holdId",id),()->{
   var h=hold(org,id,true);if(!"ACTIVE".equals(h.get("status")))throw ApiFailure.conflict("INVALID_BOOKING_HOLD_TRANSITION","Hold is not active");
   db.update("UPDATE booking_hold SET status='CANCELLED',version=version+1 WHERE organization_id=? AND id=?",org,id);
   reservations.releaseHold(org,id);audit.record(org,actor.userId(),"BOOKING_HOLD_CANCELLED","BOOKING_HOLD",id);
   return viewHold(org,id);
  });
 }
 @Transactional
 public Map<String,Object> confirmHold(UUID org,UUID id,String key){
  Actor actor=auth.actor();auth.member(org);hold(org,id,false);return idem.execute(org,actor,"confirm-hold:"+id,key,Map.of("holdId",id),()->{
   var h=hold(org,id,true);
   if(!"ACTIVE".equals(h.get("status")))throw ApiFailure.conflict("INVALID_BOOKING_HOLD_TRANSITION","Hold is not active");
   if(!((Instant)h.get("expiresAt")).isAfter(dbNow()))throw ApiFailure.conflict("BOOKING_HOLD_EXPIRED","Hold expired");
   UUID app=UUID.randomUUID();
   db.update("""
    INSERT INTO appointment(id,organization_id,service_id,location_id,customer_user_profile_id,staff_id,
      start_at,end_at,service_name_snapshot,duration_minutes_snapshot,buffer_before_minutes_snapshot,buffer_after_minutes_snapshot,
      cancellation_deadline_minutes_snapshot,reschedule_deadline_minutes_snapshot)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    """,app,org,h.get("serviceId"),h.get("locationId"),h.get("customerUserProfileId"),h.get("staffId"),h.get("startAt"),h.get("endAt"),
         h.get("serviceNameSnapshot"),h.get("durationMinutesSnapshot"),h.get("bufferBeforeMinutesSnapshot"),h.get("bufferAfterMinutesSnapshot"),
         h.get("cancellationDeadlineMinutesSnapshot"),h.get("rescheduleDeadlineMinutesSnapshot"));
   reservations.transferHold(org,id,app);
   db.update("UPDATE booking_hold SET status='CONFIRMED',appointment_id=?,version=version+1 WHERE organization_id=? AND id=?",app,org,id);
   history(org,app,null,"CONFIRMED",null);events(org,"APPOINTMENT_CONFIRMED",app);
   return viewAppointment(org,app);
  });
 }
 @Transactional
 public Map<String,Object> direct(UUID org,String key,Map<String,Object> input){
  auth.scheduler(org);Actor actor=auth.actor();return idem.execute(org,actor,"direct-booking",key,input,()->{
   UUID service=uuid(input,"serviceId"),location=uuid(input,"locationId"),customer=uuid(input,"customerUserProfileId");
   UUID staff=optionalUuid(input,"preferredStaffId");Instant start=instant(input,"startAt");
   if(db.optional("SELECT id FROM organization_membership WHERE organization_id=? AND user_profile_id=? AND status='ACTIVE'",org,customer).isEmpty())
    throw new ApiFailure(422,"CUSTOMER_NOT_ELIGIBLE","Customer has no active tenant membership");
   var s=catalog.service(org,service);var selected=allocator.allocate(org,service,location,start,staff);
   UUID id=UUID.randomUUID();Instant end=start.plusSeconds(n(s,"durationMinutes")*60L);
   db.update("""
    INSERT INTO appointment(id,organization_id,service_id,location_id,customer_user_profile_id,staff_id,
      start_at,end_at,service_name_snapshot,duration_minutes_snapshot,buffer_before_minutes_snapshot,buffer_after_minutes_snapshot,
      cancellation_deadline_minutes_snapshot,reschedule_deadline_minutes_snapshot)
    VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    """,id,org,service,location,customer,staff,start,end,s.get("name"),s.get("durationMinutes"),s.get("bufferBeforeMinutes"),s.get("bufferAfterMinutes"),
       s.get("cancellationDeadlineMinutes"),s.get("rescheduleDeadlineMinutes"));
   reservations.create(org,null,id,selected,start,end,n(s,"bufferBeforeMinutes"),n(s,"bufferAfterMinutes"));
   history(org,id,null,"CONFIRMED",null);events(org,"APPOINTMENT_CONFIRMED",id);
   return viewAppointment(org,id);
  });
 }
 @Transactional
 public Map<String,Object> get(UUID org,UUID id){appointment(org,id,false);return viewAppointment(org,id);}
 @Transactional
 public Map<String,Object> list(UUID org,boolean mine,int limit,Map<String,String> query){
  auth.member(org);if(limit<1||limit>100)throw ApiFailure.bad("limit must be 1..100");
  String status=query.get("status");
  if(status!=null&&!Set.of("CONFIRMED","CHECKED_IN","IN_PROGRESS","COMPLETED","CANCELLED","NO_SHOW").contains(status))throw ApiFailure.bad("Invalid status filter");
  Instant defaultFrom=LocalDate.now(ZoneOffset.UTC).atStartOfDay(ZoneOffset.UTC).toInstant();
  Instant from=query.containsKey("from")?parseQueryTime(query.get("from")):defaultFrom;
  Instant to=query.containsKey("to")?parseQueryTime(query.get("to")):from.plus(Duration.ofDays(30));
  if(!to.isAfter(from)||Duration.between(from,to).toDays()>93)throw ApiFailure.bad("Appointment search must cover at most 93 days");
  String role=auth.membership(org);
  boolean own=mine||!auth.elevated(org);boolean assignedStaff=own&&!mine&&"STAFF".equals(role);
  UUID actor=auth.actor().userId();
  String expectedScope=org+"|"+actor+"|"+role+"|"+own+"|"+from+"|"+to+"|"+status;
  Instant seekTime=from;UUID seekId=new UUID(0,0);
  if(query.containsKey("cursor")){
   String value=cursors.decode(query.get("cursor"));String prefix=expectedScope+"|";
   if(!value.startsWith(prefix))throw ApiFailure.bad("Pagination cursor does not match caller and filters");
   String remaining=value.substring(prefix.length());int index=remaining.lastIndexOf('|');
   try{seekTime=Instant.parse(remaining.substring(0,index));seekId=UUID.fromString(remaining.substring(index+1));}
   catch(Exception e){throw ApiFailure.bad("Invalid pagination cursor");}
  }
  var args=new ArrayList<Object>();args.add(org);args.add(from);args.add(to);
  StringBuilder sql=new StringBuilder("""
   SELECT id,organization_id,service_id,location_id,customer_user_profile_id,
          start_at,end_at,status,version FROM appointment
   WHERE organization_id=? AND start_at>=? AND start_at<?
   """);
  if(assignedStaff){
   sql.append(" AND (customer_user_profile_id=? OR staff_id IN (SELECT id FROM staff_member WHERE organization_id=? AND user_profile_id=? AND active=true))");
   args.add(actor);args.add(org);args.add(actor);
  }else if(own){sql.append(" AND customer_user_profile_id=?");args.add(actor);}
  if(status!=null){sql.append(" AND status=?");args.add(status);}
  sql.append(" AND (start_at>? OR (start_at=? AND id>?)) ORDER BY start_at,id LIMIT ?");
  args.add(seekTime);args.add(seekTime);args.add(seekId);args.add(limit+1);
  List<Map<String,Object>> results=db.many(sql.toString(),args.toArray());
  boolean hasMore=results.size()>limit;
  List<Map<String,Object>> items=hasMore?results.subList(0,limit):results;
  String next=null;
  if(hasMore){var last=items.getLast();next=cursors.encode(expectedScope+"|"+last.get("startAt")+"|"+last.get("id"));}
  Map<String,Object> page=new LinkedHashMap<>();page.put("size",limit);page.put("nextCursor",next);page.put("hasMore",hasMore);
  return Map.of("items",items,"page",page);
 }
 private Instant parseQueryTime(String s){try{return OffsetDateTime.parse(s).toInstant();}catch(Exception e){throw ApiFailure.bad("Invalid from/to timestamp");}}

 @Transactional
 public Map<String,Object> history(UUID org,UUID id,int limit){appointment(org,id,false);
  return HttpSupport.page(db.many("SELECT id,appointment_id,from_status,to_status,reason,changed_at FROM appointment_status_history WHERE organization_id=? AND appointment_id=? ORDER BY changed_at DESC,id DESC LIMIT ?",org,id,Math.min(100,Math.max(1,limit))+1),Math.min(100,Math.max(1,limit)));
 }
 @Transactional
 public Map<String,Object> command(UUID org,UUID id,String name,String etag,String key,Map<String,Object> request){
  auth.member(org);Actor actor=auth.actor();appointment(org,id,false);
  Map<String,Object> input=new LinkedHashMap<>(request);input.put("appointmentId",id.toString());input.put("ifMatch",etag);
  return idem.execute(org,actor,"appointment:"+name+":"+id,key,input,()->{
   var a=appointment(org,id,true);HttpSupport.requireMatch(etag,((Number)a.get("version")).longValue());
   String previous=a.get("status").toString();String next=switch(name){
    case "cancel" -> "CANCELLED";
    case "check-in" -> "CHECKED_IN";
    case "start" -> "IN_PROGRESS";
    case "complete" -> "COMPLETED";
    case "no-show" -> "NO_SHOW";
    case "reschedule" -> "CONFIRMED";
    default -> throw ApiFailure.bad("Unknown command");
   };
   boolean permitted=switch(name){
    case "cancel" -> Set.of("CONFIRMED","CHECKED_IN").contains(previous);
    case "reschedule","no-show","check-in" -> previous.equals("CONFIRMED");
    case "start" -> previous.equals("CHECKED_IN");
    case "complete" -> previous.equals("IN_PROGRESS");
    default -> false;
   };
   if(!permitted)throw ApiFailure.conflict("INVALID_APPOINTMENT_TRANSITION","Transition not permitted in this status");
   if(Set.of("check-in","start","complete","no-show").contains(name)&&!auth.staff(org)) throw ApiFailure.denied();
   Instant start=(Instant)a.get("startAt");Instant current=dbNow();
   int deadline="reschedule".equals(name)?n(a,"rescheduleDeadlineMinutesSnapshot"):
       n(a,"cancellationDeadlineMinutesSnapshot");
   if(Set.of("cancel","reschedule").contains(name)&&!start.isAfter(current.plusSeconds(deadline*60L)))
    throw new ApiFailure(422,"DEADLINE_PASSED","Booking change deadline has passed");
   if(name.equals("reschedule")){
    Instant changed=instant(request,"startAt");UUID location=request.get("locationId")==null?(UUID)a.get("locationId"):uuid(request,"locationId");
    UUID staff=optionalUuid(request,"preferredStaffId");
    List<Map<String,Object>> selected=allocator.allocate(org,(UUID)a.get("serviceId"),location,changed,staff);
    Instant end=changed.plusSeconds(n(a,"durationMinutesSnapshot")*60L);
    reservations.releaseAppointment(org,id);
    reservations.create(org,null,id,selected,changed,end,n(a,"bufferBeforeMinutesSnapshot"),n(a,"bufferAfterMinutesSnapshot"));
    db.update("UPDATE appointment SET start_at=?,end_at=?,location_id=?,staff_id=?,version=version+1,updated_at=clock_timestamp() WHERE organization_id=? AND id=?",changed,end,location,staff,org,id);
   }else{
    db.update("UPDATE appointment SET status=?,version=version+1,updated_at=clock_timestamp() WHERE organization_id=? AND id=?",next,org,id);
    if(name.equals("cancel"))reservations.releaseAppointment(org,id);
   }
   history(org,id,previous,next,Objects.toString(request.get("reason"),null));events(org,"APPOINTMENT_"+name.toUpperCase().replace('-','_'),id);
   return viewAppointment(org,id);
  });
 }
 // A separate transaction per claimed expired hold allows other workers/confirmations to serialize correctly.
 @Transactional
 public void expireOne(UUID org,UUID holdId){
  var h=db.one("SELECT * FROM booking_hold WHERE organization_id=? AND id=? FOR UPDATE",org,holdId);
  if("ACTIVE".equals(h.get("status"))&&!((Instant)h.get("expiresAt")).isAfter(dbNow())){
   db.update("UPDATE booking_hold SET status='EXPIRED',version=version+1 WHERE organization_id=? AND id=?",org,holdId);
   reservations.releaseHold(org,holdId);
  }
 }
}

