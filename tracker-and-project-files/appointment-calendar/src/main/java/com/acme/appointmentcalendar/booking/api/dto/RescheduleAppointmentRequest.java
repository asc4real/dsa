package com.acme.appointmentcalendar.booking.api.dto;
import jakarta.validation.constraints.*;
import java.util.*;
import java.time.Instant;
public record RescheduleAppointmentRequest(@NotNull Instant startAt,UUID locationId,
 UUID preferredStaffId,@Size(max=512) String reason){
 public Map<String,Object> toInput(){
  Map<String,Object> input=new LinkedHashMap<>();input.put("startAt",startAt.toString());
  if(locationId!=null)input.put("locationId",locationId);
  if(preferredStaffId!=null)input.put("preferredStaffId",preferredStaffId);
  if(reason!=null)input.put("reason",reason);
  return input;
 }
}
