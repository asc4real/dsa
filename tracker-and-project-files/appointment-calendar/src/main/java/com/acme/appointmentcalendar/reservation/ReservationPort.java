package com.acme.appointmentcalendar.reservation;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;
@Service
public class ReservationPort {
 private final Sql db;public ReservationPort(Sql db){this.db=db;}
 public void create(UUID org,UUID hold,UUID appointment,List<Map<String,Object>> resources,Instant start,Instant end,int before,int after){
  Instant blockedStart=start.minusSeconds(before*60L),blockedEnd=end.plusSeconds(after*60L);
  for(var resource:resources.stream().sorted(Comparator.comparing(r->r.get("id").toString())).toList()){
   // Opportunistically reclaim logically expired holds under row locks.
   // This is intentionally transaction-bound and cannot bypass the GiST exclusion constraint.
   List<Map<String,Object>> expired=db.many("""
    SELECT h.id FROM booking_hold h JOIN resource_reservation r
    ON r.organization_id=h.organization_id AND r.hold_id=h.id
    WHERE h.organization_id=? AND r.resource_id=? AND r.state='ACTIVE' AND h.status='ACTIVE'
      AND h.expires_at<=clock_timestamp() AND r.reserved_start_at<? AND r.reserved_end_at>?
    FOR UPDATE OF h SKIP LOCKED
    """,org,resource.get("id"),blockedEnd,blockedStart);
   for(var row:expired){
    UUID expiredId=(UUID)row.get("id");
    int changed=db.update("UPDATE booking_hold SET status='EXPIRED',version=version+1 WHERE organization_id=? AND id=? AND status='ACTIVE' AND expires_at<=clock_timestamp()",org,expiredId);
    if(changed==1){
     releaseHold(org,expiredId);
     db.update("INSERT INTO audit_event(id,organization_id,event_type,target_type,target_id) VALUES (?,?,'BOOKING_HOLD_EXPIRED','BOOKING_HOLD',?)",UUID.randomUUID(),org,expiredId);
    }
   }
   db.update("""
    INSERT INTO resource_reservation(id,organization_id,resource_id,hold_id,appointment_id,reserved_start_at,reserved_end_at)
    VALUES(?,?,?,?,?,?,?)
    """,UUID.randomUUID(),org,resource.get("id"),hold,appointment,blockedStart,blockedEnd);
  }
 }
 public void transferHold(UUID org,UUID hold,UUID appointment){
  db.update("""
   UPDATE resource_reservation SET appointment_id=?,hold_id=NULL
   WHERE organization_id=? AND hold_id=? AND state='ACTIVE'
   """,appointment,org,hold);
 }
 public void releaseHold(UUID org,UUID hold){
  db.update("UPDATE resource_reservation SET state='RELEASED' WHERE organization_id=? AND hold_id=? AND state='ACTIVE'",org,hold);
 }
 public void releaseAppointment(UUID org,UUID appointment){
  db.update("UPDATE resource_reservation SET state='RELEASED' WHERE organization_id=? AND appointment_id=? AND state='ACTIVE'",org,appointment);
 }
 public boolean occupied(UUID org,List<Map<String,Object>> resources,Instant start,Instant end,int before,int after){
  for(var resource:resources){
   if(!db.many("""
    SELECT r.id FROM resource_reservation r
    LEFT JOIN booking_hold h ON h.organization_id=r.organization_id AND h.id=r.hold_id
    WHERE r.organization_id=? AND r.resource_id=? AND r.state='ACTIVE'
    AND (r.hold_id IS NULL OR (h.status='ACTIVE' AND h.expires_at>clock_timestamp()))
    AND r.reserved_start_at < ? AND r.reserved_end_at > ? LIMIT 1
    """,org,resource.get("id"),end.plusSeconds(after*60L),start.minusSeconds(before*60L)).isEmpty()) return true;
  }return false;
 }
}

