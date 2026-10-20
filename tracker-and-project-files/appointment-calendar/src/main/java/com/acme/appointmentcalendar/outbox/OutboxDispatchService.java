package com.acme.appointmentcalendar.outbox;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class OutboxDispatchService {
 private final Sql db; public OutboxDispatchService(Sql db){this.db=db;}
 // Claim in a short transaction and make no external calls while holding row locks.
 @Transactional
 public List<Map<String,Object>> claim(int limit){
  List<Map<String,Object>> claimed=db.many("""
   SELECT id,organization_id,event_type,aggregate_id,payload::text AS payload_json,attempts
   FROM outbox_event
   WHERE (status='PENDING' OR (status='CLAIMED' AND claimed_until<clock_timestamp()))
     AND next_attempt_at<=clock_timestamp()
   ORDER BY next_attempt_at,id LIMIT ? FOR UPDATE SKIP LOCKED
   """,limit);
  for(var row:claimed)db.update("UPDATE outbox_event SET status='CLAIMED',claimed_until=clock_timestamp()+INTERVAL '2 minutes',attempts=attempts+1 WHERE id=?",row.get("id"));
  return claimed;
 }
 @Transactional
 public void deliver(UUID id,UUID org,UUID appointment,String event){
  // Deduplication: one notification row per event (event id used as notification id).
  db.update("""
   INSERT INTO notification(id,organization_id,appointment_id,kind,channel)
   VALUES(?,?,?,?,'EMAIL') ON CONFLICT(id) DO NOTHING
   """,id,org,appointment,event);
  db.update("UPDATE outbox_event SET status='SENT',claimed_until=NULL,last_error=NULL WHERE id=? AND status='CLAIMED'",id);
 }
 @Transactional
 public void failed(UUID id,String error){
  db.update("""
   UPDATE outbox_event SET status=CASE WHEN attempts>=8 THEN 'DEAD' ELSE 'PENDING' END,
    claimed_until=NULL,next_attempt_at=clock_timestamp()+LEAST(3600,10*CAST(POWER(2,LEAST(attempts,8)) AS integer))*INTERVAL '1 second',
    last_error=? WHERE id=? AND status='CLAIMED'
   """,error.length()>500?error.substring(0,500):error,id);
 }
}

