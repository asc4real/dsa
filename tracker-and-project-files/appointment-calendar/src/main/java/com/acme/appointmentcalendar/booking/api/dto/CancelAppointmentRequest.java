package com.acme.appointmentcalendar.booking.api.dto;
import jakarta.validation.constraints.*;
import java.util.*;
public record CancelAppointmentRequest(@Size(max=512) String reason){
 public Map<String,Object> toInput(){return reason==null?Map.of():Map.of("reason",reason);}
}
