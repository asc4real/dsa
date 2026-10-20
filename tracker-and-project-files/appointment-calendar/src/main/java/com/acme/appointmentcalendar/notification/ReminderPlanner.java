package com.acme.appointmentcalendar.notification;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ReminderPlanner {
 private final Sql db; public ReminderPlanner(Sql db){this.db=db;}
 @Transactional
 public int plan(){return db.update("""
  INSERT INTO reminder_instance(id,organization_id,appointment_id,definition_id,due_at)
  SELECT gen_random_uuid(),a.organization_id,a.id,d.id,
     a.start_at - d.lead_minutes * INTERVAL '1 minute'
  FROM appointment a JOIN reminder_definition d ON d.organization_id=a.organization_id AND d.active=true
  WHERE a.status='CONFIRMED' AND a.start_at>clock_timestamp()
  ON CONFLICT(appointment_id,definition_id) DO NOTHING
  """);}
 @Transactional
 public int dispatch(){
  List<Map<String,Object>> due=db.many("""
   SELECT id,organization_id,appointment_id FROM reminder_instance
   WHERE status='PENDING' AND due_at<=clock_timestamp() AND appointment_id IN (SELECT id FROM appointment WHERE status='CONFIRMED')
   ORDER BY due_at LIMIT 50 FOR UPDATE SKIP LOCKED
   """);
  for(var r:due){
   db.update("INSERT INTO notification(id,organization_id,appointment_id,kind,channel) VALUES(?,?,?,'APPOINTMENT_REMINDER','EMAIL') ON CONFLICT(id) DO NOTHING",r.get("id"),r.get("organizationId"),r.get("appointmentId"));
   db.update("UPDATE reminder_instance SET status='DISPATCHED' WHERE id=?",r.get("id"));
  }return due.size();
 }
}

