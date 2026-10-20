package com.acme.appointmentcalendar.operations;



import com.acme.appointmentcalendar.outbox.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;
@Component
public class OutboxWorker {
 private final OutboxDispatchService dispatcher;private final boolean enabled;
 public OutboxWorker(OutboxDispatchService d,@Value("${app.worker.enabled:true}") boolean enabled){dispatcher=d;this.enabled=enabled;}
 @Scheduled(fixedDelayString="${OUTBOX_POLL_MS:3000}")
 public void run(){
  if(!enabled)return;
  for(var row:dispatcher.claim(40)){
   UUID id=(UUID)row.get("id");
   try{dispatcher.deliver(id,(UUID)row.get("organizationId"),(UUID)row.get("aggregateId"),(String)row.get("eventType"));}
   catch(Exception ex){dispatcher.failed(id,"Delivery processing failed");}
  }
 }
}

