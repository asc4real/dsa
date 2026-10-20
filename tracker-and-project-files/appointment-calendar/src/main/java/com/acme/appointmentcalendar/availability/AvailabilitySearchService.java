package com.acme.appointmentcalendar.availability;



import com.acme.appointmentcalendar.catalog.*;
import com.acme.appointmentcalendar.reservation.*;
import com.acme.appointmentcalendar.security.*;
import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
@Service
public class AvailabilitySearchService {
 private final CatalogReader catalog;private final CandidateResourceAllocator allocator;private final Permissions auth;
 public AvailabilitySearchService(CatalogReader c,CandidateResourceAllocator allocator,Permissions a){catalog=c;this.allocator=allocator;auth=a;}
 public Map<String,Object> search(UUID org,Map<String,Object> request){
  auth.member(org);
  UUID service=asUuid(request.get("serviceId")),location=asUuid(request.get("locationId"));
  UUID staff=request.get("preferredStaffId")==null?null:asUuid(request.get("preferredStaffId"));
  LocalDate from=LocalDate.parse(Objects.toString(request.get("dateFrom"))),until=LocalDate.parse(Objects.toString(request.get("dateToExclusive")));
  if(!until.isAfter(from)||until.isAfter(from.plusDays(31)))throw ApiFailure.bad("Availability search must cover 1–31 days");
  var svc=catalog.service(org,service);var loc=catalog.location(org,location);
  ZoneId zone=ZoneId.of(Objects.toString(request.getOrDefault("displayTimeZone",loc.get("zoneId"))));
  int duration=((Number)svc.get("durationMinutes")).intValue(), before=((Number)svc.get("bufferBeforeMinutes")).intValue(),after=((Number)svc.get("bufferAfterMinutes")).intValue();
  List<Map<String,Object>> slots=new ArrayList<>();boolean truncated=false;
  outer:for(LocalDate date=from;date.isBefore(until);date=date.plusDays(1)){
   for(int minute=0;minute<1440;minute+=15){
    LocalDateTime local=date.atStartOfDay().plusMinutes(minute);
    var offsets=zone.getRules().getValidOffsets(local);
    for(ZoneOffset offset:offsets){
     Instant start=local.toInstant(offset);Instant end=start.plusSeconds(duration*60L);
     try{
      allocator.allocate(org,service,location,start,staff);
     }catch(ApiFailure rejected){continue;}
     if(slots.size()==500){truncated=true;break outer;}
     Map<String,Object> slot=new LinkedHashMap<>();
     slot.put("startAt",start);slot.put("endAt",end);
     slot.put("localStart",start.atZone(zone).toOffsetDateTime().toString());
     slot.put("localEnd",end.atZone(zone).toOffsetDateTime().toString());
     if(staff!=null)slot.put("staffId",staff);
     slots.add(slot);
    }
   }
  }
  return Map.of("serviceId",service,"locationId",location,"timeZone",zone.toString(),"generatedAt",Instant.now(),"availableSlots",slots,"truncated",truncated,"advisory",true);
 }
 private UUID asUuid(Object x){try{return UUID.fromString(x.toString());}catch(Exception e){throw ApiFailure.bad("Expected UUID");}}
}

