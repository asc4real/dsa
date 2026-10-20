package com.acme.appointmentcalendar;
import com.acme.appointmentcalendar.shared.*;



import com.acme.appointmentcalendar.organization.*;
import com.acme.appointmentcalendar.booking.*;
import com.acme.appointmentcalendar.availability.*;
import com.acme.appointmentcalendar.security.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class OperationRouter {
 private final AdminFacade admin;private final BookingService booking;private final AvailabilitySearchService availability;
 public OperationRouter(AdminFacade admin,BookingService booking,AvailabilitySearchService availability){
  this.admin=admin;this.booking=booking;this.availability=availability;
 }
 public Object route(String operation,Map<String,String> path,Map<String,Object> body,String match,String key,int limit,Map<String,String> query){
  Map<String,Object> data=body==null?Map.of():body;
  Map<String,Set<String>> fields=Map.ofEntries(
   Map.entry("createBookingHold",Set.of("serviceId","locationId","startAt","preferredStaffId")),
   Map.entry("createDirectBooking",Set.of("serviceId","locationId","customerUserProfileId","startAt","preferredStaffId")),
   Map.entry("rescheduleAppointment",Set.of("startAt","locationId","preferredStaffId","reason")),
   Map.entry("cancelAppointment",Set.of("reason")),
   Map.entry("checkinAppointment",Set.of()),
   Map.entry("startAppointment",Set.of()),
   Map.entry("completeAppointment",Set.of()),
   Map.entry("noshowAppointment",Set.of("reason")),
   Map.entry("confirmBookingHold",Set.of()),
   Map.entry("cancelBookingHold",Set.of())
  );
  if(fields.containsKey(operation))for(String supplied:data.keySet())
   if(!fields.get(operation).contains(supplied)) throw ApiFailure.bad("Unsupported or protected field: "+supplied);
  if(operation.equals("searchAvailability"))return availability.search(uuid(path,"orgId"),data);
  if(operation.equals("createBookingHold"))return booking.createHold(uuid(path,"orgId"),key,data);
  if(operation.equals("getBookingHold"))return booking.getHold(uuid(path,"orgId"),uuid(path,"holdId"));
  if(operation.equals("confirmBookingHold"))return booking.confirmHold(uuid(path,"orgId"),uuid(path,"holdId"),key);
  if(operation.equals("cancelBookingHold"))return booking.cancelHold(uuid(path,"orgId"),uuid(path,"holdId"),key);
  if(operation.equals("listAppointments"))return booking.list(uuid(path,"orgId"),false,limit,query);
  if(operation.equals("listMyAppointments"))return booking.list(uuid(path,"orgId"),true,limit,query);
  if(operation.equals("createDirectBooking"))return booking.direct(uuid(path,"orgId"),key,data);
  if(operation.equals("getAppointment"))return booking.get(uuid(path,"orgId"),uuid(path,"appointmentId"));
  if(operation.equals("getAppointmentHistory"))return booking.history(uuid(path,"orgId"),uuid(path,"appointmentId"),limit);
  if(Set.of("rescheduleAppointment","cancelAppointment","checkinAppointment","startAppointment","completeAppointment","noshowAppointment").contains(operation)){
   String command=switch(operation){
    case "rescheduleAppointment" -> "reschedule"; case "cancelAppointment" -> "cancel";
    case "checkinAppointment" -> "check-in";case "startAppointment" -> "start";
    case "completeAppointment" -> "complete";default -> "no-show";
   };
   return booking.command(uuid(path,"orgId"),uuid(path,"appointmentId"),command,match,key,data);
  }
  return admin.run(operation,path,data,match,limit);
 }
 private UUID uuid(Map<String,String> paths,String key){try{return UUID.fromString(paths.get(key));}catch(Exception e){throw ApiFailure.bad("Invalid "+key);}}
}

