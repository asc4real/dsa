package com.acme.appointmentcalendar.operations;



import com.acme.appointmentcalendar.notification.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;
@Component
public class NotificationWorker {
 private final NotificationDeliveryService delivery;private final EmailGateway mail;private final boolean enabled;private final boolean emailEnabled;
 public NotificationWorker(NotificationDeliveryService d,EmailGateway m,@Value("${app.worker.enabled:true}") boolean enabled,@Value("${app.mail.enabled:false}") boolean emailEnabled){delivery=d;mail=m;this.enabled=enabled;this.emailEnabled=emailEnabled;}
 @Scheduled(fixedDelayString="${NOTIFICATION_POLL_MS:15000}")
 public void run(){if(!enabled||!emailEnabled)return;
  for(var n:delivery.claim(10)){
   UUID id=(UUID)n.get("id");
   try{mail.send(n.get("email").toString(),"Appointment update", "Your appointment has an update. Sign in to view details.");delivery.success(id);}
   catch(Exception e){delivery.failed(id);}
  }
 }
}

