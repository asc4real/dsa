package com.acme.appointmentcalendar.booking.api.dto;
import jakarta.validation.constraints.*;
import java.util.*;
import java.time.Instant;
public record CreateDirectBookingRequest(@NotNull UUID serviceId,@NotNull UUID locationId,
 @NotNull UUID customerUserProfileId,@NotNull Instant startAt,UUID preferredStaffId){
 public Map<String,Object> toInput(){
  Map<String,Object> input=new LinkedHashMap<>();
  input.put("serviceId",serviceId);input.put("locationId",locationId);
  input.put("customerUserProfileId",customerUserProfileId);input.put("startAt",startAt.toString());
  if(preferredStaffId!=null)input.put("preferredStaffId",preferredStaffId);
  return input;
 }
}
