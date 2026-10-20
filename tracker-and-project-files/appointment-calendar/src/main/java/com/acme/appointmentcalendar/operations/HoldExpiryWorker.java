package com.acme.appointmentcalendar.operations;



import com.acme.appointmentcalendar.booking.*;
import com.acme.appointmentcalendar.shared.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;
@Component
public class HoldExpiryWorker {
 private final Sql db;private final BookingService booking;private final boolean enabled;
 public HoldExpiryWorker(Sql db,BookingService booking,@Value("${app.worker.enabled:true}") boolean enabled){this.db=db;this.booking=booking;this.enabled=enabled;}
 @Scheduled(fixedDelayString="${HOLD_EXPIRY_POLL_MS:5000}")
 public void expire(){
  if(!enabled)return;
  for(var entry:db.many("SELECT organization_id,id FROM booking_hold WHERE status='ACTIVE' AND expires_at <= clock_timestamp() ORDER BY expires_at LIMIT 50")){
   try{booking.expireOne((UUID)entry.get("organizationId"),(UUID)entry.get("id"));}
   catch(Exception ignored){ /* Next polling pass retries safely; operational metrics/logging should report failures. */ }
  }
 }
}

