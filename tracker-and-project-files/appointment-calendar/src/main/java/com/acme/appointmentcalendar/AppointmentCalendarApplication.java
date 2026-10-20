package com.acme.appointmentcalendar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class AppointmentCalendarApplication {
 public static void main(String[] args) { SpringApplication.run(AppointmentCalendarApplication.class, args); }
}
