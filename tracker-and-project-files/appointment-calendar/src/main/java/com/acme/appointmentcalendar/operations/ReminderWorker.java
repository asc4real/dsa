package com.acme.appointmentcalendar.operations;



import com.acme.appointmentcalendar.notification.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
@Component
public class ReminderWorker {
 private final ReminderPlanner planner; private final boolean enabled;
 public ReminderWorker(ReminderPlanner planner,@Value("${app.worker.enabled:true}") boolean enabled){this.planner=planner;this.enabled=enabled;}
 @Scheduled(fixedDelayString="${REMINDER_PLAN_POLL_MS:60000}")
 public void plan(){if(enabled)planner.plan();}
 @Scheduled(fixedDelayString="${REMINDER_POLL_MS:15000}")
 public void dispatch(){if(enabled)planner.dispatch();}
}

