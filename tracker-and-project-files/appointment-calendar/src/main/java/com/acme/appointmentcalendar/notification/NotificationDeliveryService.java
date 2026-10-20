package com.acme.appointmentcalendar.notification;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class NotificationDeliveryService {
 private final Sql db;public NotificationDeliveryService(Sql db){this.db=db;}
 @Transactional
 public List<Map<String,Object>> claim(int count){
  List<Map<String,Object>> entries=db.many("""
   SELECT n.id,n.appointment_id,u.email,n.kind FROM notification n
   JOIN appointment a ON a.organization_id=n.organization_id AND a.id=n.appointment_id
   JOIN user_profile u ON u.id=a.customer_user_profile_id
   WHERE (n.status='PENDING' OR (n.status='CLAIMED' AND n.claimed_until<clock_timestamp())) AND n.next_attempt_at<=clock_timestamp() AND u.email IS NOT NULL
   ORDER BY n.created_at LIMIT ? FOR UPDATE OF n SKIP LOCKED
   """,count);
  for(var e:entries){
   db.update("UPDATE notification SET status='CLAIMED',attempts=attempts+1,claimed_until=clock_timestamp()+INTERVAL '2 minutes' WHERE id=?",e.get("id"));
   db.update("INSERT INTO notification_delivery(id,notification_id,provider,status,attempts) VALUES(?,?,'SMTP','PENDING',1)",java.util.UUID.randomUUID(),e.get("id"));
  }
  return entries;
 }
 @Transactional
 public void success(UUID id){
  db.update("UPDATE notification SET status='SENT',claimed_until=NULL WHERE id=?",id);
  db.update("UPDATE notification_delivery SET status='SENT' WHERE id=(SELECT id FROM notification_delivery WHERE notification_id=? ORDER BY created_at DESC,id DESC LIMIT 1)",id);
 }
 @Transactional
 public void failed(UUID id){
  db.update("UPDATE notification SET status=CASE WHEN attempts>=8 THEN 'DEAD' ELSE 'PENDING' END, claimed_until=NULL,next_attempt_at=clock_timestamp()+LEAST(3600,10*CAST(POWER(2,LEAST(attempts,8)) AS integer))*INTERVAL '1 second' WHERE id=?",id);
  db.update("UPDATE notification_delivery SET status='FAILED',last_error='Provider delivery failed' WHERE id=(SELECT id FROM notification_delivery WHERE notification_id=? ORDER BY created_at DESC,id DESC LIMIT 1)",id);
 }
}

