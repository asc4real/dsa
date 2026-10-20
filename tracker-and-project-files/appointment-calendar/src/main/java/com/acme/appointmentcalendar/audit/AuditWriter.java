package com.acme.appointmentcalendar.audit;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class AuditWriter {
 private final Sql db; public AuditWriter(Sql db){this.db=db;}
 public void record(UUID org,UUID actor,String event,String target,UUID targetId){
  db.update("INSERT INTO audit_event(id,organization_id,actor_user_profile_id,event_type,target_type,target_id) VALUES(?,?,?,?,?,?)",
   UUID.randomUUID(),org,actor,event,target,targetId);
 }
}

