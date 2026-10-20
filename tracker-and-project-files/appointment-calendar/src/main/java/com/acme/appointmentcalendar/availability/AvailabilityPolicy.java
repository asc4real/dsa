package com.acme.appointmentcalendar.availability;



import com.acme.appointmentcalendar.shared.*;
import com.acme.appointmentcalendar.catalog.*;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
@Service
public class AvailabilityPolicy {
 private final Sql db; private final CatalogReader catalog;
 public AvailabilityPolicy(Sql db,CatalogReader catalog){this.db=db;this.catalog=catalog;}
 public void validate(UUID org,UUID svc,UUID location,Instant start,List<Map<String,Object>> resources){
  Map<String,Object> service=catalog.service(org,svc);Map<String,Object> loc=catalog.location(org,location);
  catalog.serviceAtLocation(org,svc,location);
  Instant now=Instant.now();
  int min=((Number)service.get("minBookingNoticeMinutes")).intValue();
  int horizon=((Number)service.get("maxBookingHorizonDays")).intValue();
  if(start.isBefore(now.plusSeconds(min*60L))||start.isAfter(now.plus(Duration.ofDays(horizon))))
   throw new ApiFailure(422,"BOOKING_HORIZON_EXCEEDED","Time is outside the permitted booking window");
  int duration=((Number)service.get("durationMinutes")).intValue();
  int before=((Number)service.get("bufferBeforeMinutes")).intValue();int after=((Number)service.get("bufferAfterMinutes")).intValue();
  Instant blockStart=start.minusSeconds(before*60L);Instant blockEnd=start.plusSeconds((duration+after)*60L);
  ZoneId zone=ZoneId.of((String)loc.get("zoneId"));
  // Use zoned instants so DST gaps/overlaps have unambiguous offsets.
  ZonedDateTime a=blockStart.atZone(zone), b=blockEnd.atZone(zone);
  if(!a.toLocalDate().equals(b.toLocalDate())) throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","Overnight resource blocks require a dedicated scheduling policy");
  int day=a.getDayOfWeek().getValue();
  var orgHours=db.many("SELECT * FROM organization_business_hours WHERE organization_id=? AND day_of_week=?",org,day);
  if(!orgHours.isEmpty()&&!insideAny(orgHours,"opensAt","closesAt",a.toLocalTime(),b.toLocalTime()))
   throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","Outside organization hours");
  var locationHours=db.many("SELECT * FROM location_opening_hours WHERE organization_id=? AND location_id=? AND day_of_week=?",org,location,day);
  if(!locationHours.isEmpty()&&!insideAny(locationHours,"opensAt","closesAt",a.toLocalTime(),b.toLocalTime()))
   throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","Outside location hours");
  for(var resource:resources){
   UUID id=(UUID)resource.get("id");
   var rules=db.many("SELECT * FROM availability_rule WHERE organization_id=? AND resource_id=? AND day_of_week=? AND active=true",org,id,day);
   if(!insideAny(rules,"startsAt","endsAt",a.toLocalTime(),b.toLocalTime()))
    throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","Resource has no active availability covering this time");
   if(!db.many("""
      SELECT id FROM availability_exception WHERE organization_id=? AND resource_id=? AND kind='UNAVAILABLE'
      AND start_at < ? AND end_at > ? LIMIT 1
      """,org,id,blockEnd,blockStart).isEmpty())
    throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","Resource exception blocks this time");
   if(resource.get("staffId")!=null && !db.many("""
      SELECT id FROM staff_time_off WHERE organization_id=? AND staff_id=? AND status='ACTIVE'
      AND start_at < ? AND end_at > ? LIMIT 1
      """,org,resource.get("staffId"),blockEnd,blockStart).isEmpty())
    throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","Staff time off blocks this time");
  }
 }
 private boolean insideAny(List<Map<String,Object>> rules,String from,String until,LocalTime start,LocalTime end){
  for(var r:rules){LocalTime s=(LocalTime)r.get(from),e=(LocalTime)r.get(until);
   if(!start.isBefore(s) && !end.isAfter(e))return true;
  }return false;
 }
}

