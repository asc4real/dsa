package com.acme.appointmentcalendar.operations;

import com.acme.appointmentcalendar.shared.Sql;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Gauge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public final class SchedulingMetrics {
 private final Sql db;
 private final AtomicInteger pendingOutbox=new AtomicInteger();
 private final AtomicInteger activeHolds=new AtomicInteger();
 public SchedulingMetrics(Sql db,MeterRegistry metrics){
  this.db=db;
  Gauge.builder("appointment_outbox_pending",pendingOutbox,AtomicInteger::get)
     .description("Pending durable outbox events").register(metrics);
  Gauge.builder("appointment_active_holds",activeHolds,AtomicInteger::get)
     .description("Currently active holds (database snapshot)").register(metrics);
 }
 @Scheduled(fixedDelayString="${METRICS_REFRESH_MS:30000}")
 void refresh(){
  try{
   pendingOutbox.set(((Number)db.one("SELECT COUNT(*) AS n FROM outbox_event WHERE status IN ('PENDING','CLAIMED')").get("n")).intValue());
   activeHolds.set(((Number)db.one("SELECT COUNT(*) AS n FROM booking_hold WHERE status='ACTIVE' AND expires_at>clock_timestamp()").get("n")).intValue());
  }catch(Exception ignored){ /* Gauge remains at last known value; infrastructure alerts use DB health separately. */ }
 }
}
