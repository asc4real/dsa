package com.acme.appointmentcalendar.organization;



import com.acme.appointmentcalendar.shared.*;
import com.acme.appointmentcalendar.security.*;
import com.acme.appointmentcalendar.catalog.*;
import com.acme.appointmentcalendar.audit.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;
@Service
public class AdminFacade {
 private final Sql db;private final Permissions auth;private final AuditWriter audit;
 public AdminFacade(Sql db,Permissions auth,AuditWriter audit){this.db=db;this.auth=auth;this.audit=audit;}
 record Table(String name,String idPath,List<String> columns,boolean deactivate) {}
 private static final Map<String,Table> TABLES=Map.of(
  "Location",new Table("location","locationId",List.of("name","zoneId","active"),true),
  "StaffMember",new Table("staff_member","staffId",List.of("displayName","userProfileId","active"),true),
  "Resource",new Table("calendar_resource","resourceId",List.of("name","type","locationId","active"),true),
  "Service",new Table("bookable_service","serviceId",List.of("name","description","durationMinutes","bufferBeforeMinutes","bufferAfterMinutes","minBookingNoticeMinutes","maxBookingHorizonDays","cancellationDeadlineMinutes","rescheduleDeadlineMinutes","capacity","active"),true),
  "Membership",new Table("organization_membership","membershipId",List.of("userProfileId","role","status"),false),
  "AvailabilityRule",new Table("availability_rule","ruleId",List.of("dayOfWeek","startsAt","endsAt","active"),false),
  "AvailabilityException",new Table("availability_exception","exceptionId",List.of("startAt","endAt","kind"),false),
  "StaffTimeOff",new Table("staff_time_off","timeOffId",List.of("startAt","endAt","status"),false),
  "ReminderDefinition",new Table("reminder_definition","reminderId",List.of("name","leadMinutes","channel","active"),false)
 );
 private String column(String camel){return camel.replaceAll("([a-z0-9])([A-Z])","$1_$2").toLowerCase(Locale.ROOT);}
 private UUID id(Map<String,String> vars,String key){try{return UUID.fromString(vars.get(key));}catch(Exception e){throw ApiFailure.bad("Invalid ID: "+key);}}
 private UUID org(Map<String,String> v){return id(v,"orgId");}
 private String type(String op){
  for(String suffix:List.of("Location","StaffMember","Resource","Service","Membership","AvailabilityRule","AvailabilityException","StaffTimeOff","ReminderDefinition"))
   if(op.endsWith(suffix)||op.endsWith(suffix+"s"))return suffix;
  throw ApiFailure.bad("Unsupported operation");
 }
 private Table tbl(String type){return TABLES.get(type);}
 private Map<String,Object> single(Table t,UUID org,UUID id){return db.one("SELECT * FROM "+t.name+" WHERE organization_id=? AND id=?",org,id);}
 private void guardWrite(UUID org){auth.admin(org);}
 @Transactional
 public Object run(String op,Map<String,String> vars,Map<String,Object> body,String ifMatch,int limit){
  if(op.equals("getCurrentUser"))return db.one("SELECT id,issuer,subject,display_name,email FROM user_profile WHERE id=?",auth.actor().userId());
  if(op.equals("listMyMemberships"))return HttpSupport.page(db.many("SELECT * FROM organization_membership WHERE user_profile_id=? AND status='ACTIVE' ORDER BY id LIMIT ?",auth.actor().userId(),limit+1),limit);
  if(op.equals("createOrganization")){auth.platform();return createOrganization(body);}
  if(op.equals("listPlatformOrganizations")){auth.platform();return HttpSupport.page(db.many("SELECT * FROM organization ORDER BY id LIMIT ?",limit+1),limit);}
  UUID tenant=org(vars);
  if(op.equals("getOrganization")){auth.member(tenant);return db.one("SELECT * FROM organization WHERE id=?",tenant);}
  if(op.equals("updateOrganization")){guardWrite(tenant);return editOrg(tenant,body,ifMatch);}
  if(op.equals("getOrganizationHours")||op.equals("replaceOrganizationHours"))return hours(tenant,null,op,body,ifMatch);
  if(op.equals("getLocationOpeningHours")||op.equals("replaceLocationOpeningHours"))return hours(tenant,id(vars,"locationId"),op,body,ifMatch);
  if(op.equals("getServiceResourceRequirements")||op.equals("replaceServiceResourceRequirements"))return requirements(tenant,id(vars,"serviceId"),op,body,ifMatch);
  if(op.equals("listAuditEvents")){auth.admin(tenant);return HttpSupport.page(db.many("SELECT id,organization_id,event_type,target_type,target_id,occurred_at FROM audit_event WHERE organization_id=? ORDER BY occurred_at DESC LIMIT ?",tenant,limit+1),limit);}
  if(op.equals("listAppointmentNotifications")){auth.scheduler(tenant);UUID appt=id(vars,"appointmentId");db.one("SELECT id FROM appointment WHERE organization_id=? AND id=?",tenant,appt);return HttpSupport.page(db.many("SELECT id,appointment_id,kind,channel,status,created_at FROM notification WHERE organization_id=? AND appointment_id=? ORDER BY created_at DESC LIMIT ?",tenant,appt,limit+1),limit);}
  if(op.equals("revokeMembership")){guardWrite(tenant);UUID membership=id(vars,"membershipId");var old=single(tbl("Membership"),tenant,membership);HttpSupport.requireMatch(ifMatch,((Number)old.get("version")).longValue());
   preventLastAdmin(tenant,old);
   db.update("UPDATE organization_membership SET status='REVOKED',version=version+1 WHERE organization_id=? AND id=?",tenant,membership);
   audit.record(tenant,auth.actor().userId(),"MEMBERSHIP_REVOKED","MEMBERSHIP",membership);return single(tbl("Membership"),tenant,membership);
  }
  if(op.equals("cancelStaffTimeOff")){auth.admin(tenant);UUID key=id(vars,"timeOffId");var row=single(tbl("StaffTimeOff"),tenant,key);HttpSupport.requireMatch(ifMatch,((Number)row.get("version")).longValue());
   db.update("UPDATE staff_time_off SET status='CANCELLED',version=version+1 WHERE organization_id=? AND id=?",tenant,key);return single(tbl("StaffTimeOff"),tenant,key);}
  if(op.equals("listStaff")){ auth.member(tenant);return HttpSupport.page(db.many("SELECT * FROM staff_member WHERE organization_id=? ORDER BY id LIMIT ?",tenant,limit+1),limit); }
  if(op.startsWith("list")){
   auth.member(tenant);String t=type(op);
   if(t.equals("Membership")||t.equals("ReminderDefinition")||t.equals("StaffTimeOff"))guardWrite(tenant);
   Table table=tbl(t);String scope="";Object[] args;
   if(t.equals("AvailabilityRule")||t.equals("AvailabilityException")){scope=" AND resource_id=?";args=new Object[]{tenant,id(vars,"resourceId"),limit+1};}
   else if(t.equals("StaffTimeOff")){scope=" AND staff_id=?";args=new Object[]{tenant,id(vars,"staffId"),limit+1};}
   else {args=new Object[]{tenant,limit+1};}
   return HttpSupport.page(db.many("SELECT * FROM "+table.name+" WHERE organization_id=?"+scope+" ORDER BY id LIMIT ?",args),limit);
  }
  if(op.startsWith("get")){auth.member(tenant);Table t=tbl(type(op));return single(t,tenant,id(vars,t.idPath));}
  if(op.startsWith("create")){guardWrite(tenant);Table t=tbl(type(op));return createRow(t,tenant,vars,body);}
  if(op.startsWith("update")){guardWrite(tenant);Table t=tbl(type(op));UUID key=id(vars,t.idPath);
    var old=single(t,tenant,key);HttpSupport.requireMatch(ifMatch,((Number)old.get("version")).longValue());
    if(t.name.equals("organization_membership"))preventLastAdmin(tenant,old);
    return updateRow(t,tenant,key,body);
  }
  if(op.startsWith("deactivate")){guardWrite(tenant);Table t=tbl(type(op));UUID key=id(vars,t.idPath);
   var old=single(t,tenant,key);HttpSupport.requireMatch(ifMatch,((Number)old.get("version")).longValue());
   return updateRow(t,tenant,key,Map.of("active",false));
  }
  if(op.startsWith("delete")){guardWrite(tenant);Table t=tbl(type(op));UUID key=id(vars,t.idPath);
   var old=single(t,tenant,key);HttpSupport.requireMatch(ifMatch,((Number)old.get("version")).longValue());
   db.update("DELETE FROM "+t.name+" WHERE organization_id=? AND id=?",tenant,key);return Map.of();
  }
  throw new ApiFailure(501,"NOT_IMPLEMENTED","Operation not implemented: "+op);
 }
 private void preventLastAdmin(UUID org,Map<String,Object> row){
  if(!"ORGANIZATION_ADMIN".equals(row.get("role"))||!"ACTIVE".equals(row.get("status")))return;
  long admins=((Number)db.one("SELECT COUNT(*) AS n FROM organization_membership WHERE organization_id=? AND role='ORGANIZATION_ADMIN' AND status='ACTIVE'",org).get("n")).longValue();
  if(admins<=1)throw ApiFailure.conflict("LAST_ADMIN","Cannot change last organization administrator");
 }
 private Object createOrganization(Map<String,Object> body){
  String name=Objects.toString(body.get("name"),"").trim();if(name.isEmpty()||name.length()>160)throw ApiFailure.bad("Invalid name");
  String zone=Objects.toString(body.getOrDefault("defaultZoneId","UTC"));ZoneId.of(zone);
  UUID org=UUID.randomUUID();db.update("INSERT INTO organization(id,name,default_zone_id) VALUES(?,?,?)",org,name,zone);
  db.update("INSERT INTO organization_membership(id,organization_id,user_profile_id,role,status) VALUES(?,?,?,'ORGANIZATION_ADMIN','ACTIVE')",UUID.randomUUID(),org,auth.actor().userId());
  return db.one("SELECT * FROM organization WHERE id=?",org);
 }
 private Object editOrg(UUID org,Map<String,Object> body,String match){
  var old=db.one("SELECT * FROM organization WHERE id=? FOR UPDATE",org);HttpSupport.requireMatch(match,((Number)old.get("version")).longValue());
  for(String key:body.keySet())if(!Set.of("name","defaultZoneId","status").contains(key))throw ApiFailure.bad("Unknown mutable field: "+key);
  if(body.containsKey("defaultZoneId"))ZoneId.of(body.get("defaultZoneId").toString());
  for(var e:body.entrySet()) db.update("UPDATE organization SET "+column(e.getKey())+"=?,version=version+1 WHERE id=?",e.getValue(),org);
  return db.one("SELECT * FROM organization WHERE id=?",org);
 }
 private Object hours(UUID org,UUID location,String op,Map<String,Object> body,String match){
  auth.member(org);boolean replace=op.startsWith("replace");String base=location==null?"organization_business_hours":"location_opening_hours";
  if(replace){guardWrite(org);var old=location==null?db.one("SELECT version FROM organization WHERE id=? FOR UPDATE",org):db.one("SELECT version FROM location WHERE organization_id=? AND id=? FOR UPDATE",org,location);
   HttpSupport.requireMatch(match,((Number)old.get("version")).longValue());
   if(!(body.get("windows") instanceof List<?> windows))throw ApiFailure.bad("windows array required");
   db.update("DELETE FROM "+base+" WHERE organization_id=?"+(location==null?"":" AND location_id=?"),location==null?new Object[]{org}:new Object[]{org,location});
   for(Object w:windows){if(!(w instanceof Map<?,?> win))throw ApiFailure.bad("Invalid window");
    int day=Integer.parseInt(win.get("dayOfWeek").toString());LocalTime from=LocalTime.parse(win.get("opensAt").toString()), to=LocalTime.parse(win.get("closesAt").toString());
    if(day<1||day>7||!to.isAfter(from))throw ApiFailure.bad("Invalid hours");
    if(location==null)db.update("INSERT INTO organization_business_hours(id,organization_id,day_of_week,opens_at,closes_at) VALUES(?,?,?,?,?)",UUID.randomUUID(),org,day,from,to);
    else db.update("INSERT INTO location_opening_hours(id,organization_id,location_id,day_of_week,opens_at,closes_at) VALUES(?,?,?,?,?,?)",UUID.randomUUID(),org,location,day,from,to);
   }
   if(location==null)db.update("UPDATE organization SET version=version+1 WHERE id=?",org);
   else db.update("UPDATE location SET version=version+1 WHERE organization_id=? AND id=?",org,location);
  }
  var items=db.many("SELECT day_of_week,opens_at,closes_at FROM "+base+" WHERE organization_id=?"+(location==null?"":" AND location_id=?")+" ORDER BY day_of_week,opens_at",location==null?new Object[]{org}:new Object[]{org,location});
  return Map.of("windows",items,"version",location==null?db.one("SELECT version FROM organization WHERE id=?",org).get("version"):db.one("SELECT version FROM location WHERE organization_id=? AND id=?",org,location).get("version"));
 }
 private Object requirements(UUID org,UUID service,String op,Map<String,Object> body,String match){
  auth.member(org);
  if(op.startsWith("replace")){
   guardWrite(org);var s=db.one("SELECT version FROM bookable_service WHERE organization_id=? AND id=? FOR UPDATE",org,service);
   HttpSupport.requireMatch(match,((Number)s.get("version")).longValue());
   if(!(body.get("requirements") instanceof List<?> items))throw ApiFailure.bad("requirements array required");
   db.update("DELETE FROM service_requirement_eligible_resource WHERE organization_id=? AND requirement_id IN (SELECT id FROM service_resource_requirement WHERE organization_id=? AND service_id=?)",org,org,service);
   db.update("DELETE FROM service_resource_requirement WHERE organization_id=? AND service_id=?",org,service);
   for(Object item:items){if(!(item instanceof Map<?,?> r))throw ApiFailure.bad("Invalid requirement");
    UUID key=UUID.randomUUID();int qty=Integer.parseInt(r.get("quantity").toString());
    if(qty<1||qty>8)throw ApiFailure.bad("Invalid resource quantity");
    db.update("INSERT INTO service_resource_requirement(id,organization_id,service_id,requirement_key,resource_type,quantity) VALUES(?,?,?,?,?,?)",key,org,service,r.get("requirementKey"),r.get("resourceType"),qty);
    if(!(r.get("eligibleResourceIds") instanceof List<?> ids)||ids.isEmpty())throw ApiFailure.bad("eligibleResourceIds required");
    for(Object v:ids){
     UUID candidate=UUID.fromString(v.toString());
     var resource=db.one("SELECT type,active FROM calendar_resource WHERE organization_id=? AND id=?",org,candidate);
     if(!Boolean.TRUE.equals(resource.get("active")) || !resource.get("type").equals(r.get("resourceType")))
      throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","Eligible resources must be active and match required type");
     db.update("INSERT INTO service_requirement_eligible_resource(organization_id,requirement_id,resource_id) VALUES(?,?,?)",org,key,candidate);
    }
   }
   db.update("UPDATE bookable_service SET version=version+1 WHERE organization_id=? AND id=?",org,service);
  }
  var rules=db.many("SELECT id,requirement_key,resource_type,quantity FROM service_resource_requirement WHERE organization_id=? AND service_id=?",org,service);
  for(var rule:rules){var ids=db.many("SELECT resource_id FROM service_requirement_eligible_resource WHERE organization_id=? AND requirement_id=?",org,rule.get("id"));
   rule.put("eligibleResourceIds",ids.stream().map(x->x.get("resourceId")).toList());}
  return Map.of("requirements",rules);
 }
 private Object createRow(Table t,UUID org,Map<String,String> vars,Map<String,Object> body){
  UUID id=UUID.randomUUID();Map<String,Object> payload=new LinkedHashMap<>(body);
  if(t.name.equals("availability_rule")||t.name.equals("availability_exception"))payload.put("resourceId",id(vars,"resourceId"));
  if(t.name.equals("staff_time_off"))payload.put("staffId",id(vars,"staffId"));
  if(t.name.equals("calendar_resource")&&"STAFF_MEMBER".equals(body.get("type")))throw ApiFailure.bad("STAFF_MEMBER resources are provisioned with staff");
  if(t.name.equals("organization_membership")){
   if(!Set.of("CUSTOMER","STAFF","SCHEDULER","ORGANIZATION_ADMIN").contains(body.get("role")))throw ApiFailure.bad("Invalid role");
   payload.putIfAbsent("status","ACTIVE");
  }
  if(t.name.equals("bookable_service")){
   if(!Integer.valueOf(1).equals(Integer.valueOf(body.getOrDefault("capacity",1).toString())))throw ApiFailure.bad("Only capacity=1 is supported");
   if(!body.containsKey("name")||!body.containsKey("durationMinutes"))throw ApiFailure.bad("name and durationMinutes required");
  }
  if(t.name.equals("location")&&!payload.containsKey("zoneId"))payload.put("zoneId","UTC");
  if(t.name.equals("location"))ZoneId.of(payload.get("zoneId").toString());
  if(t.name.equals("bookable_service")){int minutes=Integer.parseInt(payload.get("durationMinutes").toString());if(minutes<1||minutes>720)throw ApiFailure.bad("Invalid durationMinutes");}
  if(t.name.equals("staff_member")&&!payload.containsKey("displayName"))throw ApiFailure.bad("displayName required");
  if(t.name.equals("calendar_resource")&&(!payload.containsKey("locationId")||!payload.containsKey("type")))throw ApiFailure.bad("locationId and type required");
  List<String> columns=new ArrayList<>(List.of("id","organization_id"));List<Object> values=new ArrayList<>(List.of(id,org));
  for(var entry:payload.entrySet()){
   if(entry.getKey().equals("locationIds"))continue;
   if(!t.columns.contains(entry.getKey()) && !Set.of("resourceId","staffId").contains(entry.getKey()))throw ApiFailure.bad("Field not allowed: "+entry.getKey());
   columns.add(column(entry.getKey()));values.add(convert(entry.getKey(),entry.getValue()));
  }
  db.update("INSERT INTO "+t.name+"("+String.join(",",columns)+") VALUES("+String.join(",",Collections.nCopies(values.size(),"?"))+")",values.toArray());
  if(t.name.equals("bookable_service")){
   if(!(body.get("locationIds") instanceof List<?> ids)||ids.isEmpty())throw ApiFailure.bad("locationIds required");
   for(Object loc:ids) db.update("INSERT INTO service_location(organization_id,service_id,location_id) VALUES(?,?,?)",org,id,UUID.fromString(loc.toString()));
  }
  if(t.name.equals("staff_member")){
   // A staff calendar resource is instantiated only when a location is configured.
   var loc=db.many("SELECT id FROM location WHERE organization_id=? AND active=true ORDER BY id LIMIT 1",org);
   if(loc.isEmpty())throw new ApiFailure(422,"LOCATION_REQUIRED","Create a location before registering staff");
   db.update("INSERT INTO calendar_resource(id,organization_id,name,type,location_id,staff_id) VALUES(?,?,?,'STAFF_MEMBER',?,?)",UUID.randomUUID(),org,payload.get("displayName"),loc.getFirst().get("id"),id);
  }
  audit.record(org,auth.actor().userId(),t.name.toUpperCase(Locale.ROOT)+"_CREATED",t.name,id);
  return single(t,org,id);
 }
 private Object convert(String key,Object value){
  if(value==null)return null;
  if(Set.of("locationId","resourceId","staffId","userProfileId").contains(key))return UUID.fromString(value.toString());
  if(Set.of("startAt","endAt").contains(key))return OffsetDateTime.parse(value.toString()).toInstant();
  if(Set.of("opensAt","closesAt","startsAt","endsAt").contains(key))return LocalTime.parse(value.toString());
  return value;
 }
 private Object updateRow(Table t,UUID org,UUID id,Map<String,Object> body){
  if(body.isEmpty())throw ApiFailure.bad("No update fields");
  for(var e:body.entrySet()){
   if(!t.columns.contains(e.getKey()))throw ApiFailure.bad("Field not allowed: "+e.getKey());
   if(e.getKey().equals("userProfileId")&&t.name.equals("organization_membership"))throw ApiFailure.bad("Membership subject cannot change");
   db.update("UPDATE "+t.name+" SET "+column(e.getKey())+"=?,version=version+1 WHERE organization_id=? AND id=?",convert(e.getKey(),e.getValue()),org,id);
  }
  audit.record(org,auth.actor().userId(),t.name.toUpperCase(Locale.ROOT)+"_UPDATED",t.name,id);
  return single(t,org,id);
 }
}

