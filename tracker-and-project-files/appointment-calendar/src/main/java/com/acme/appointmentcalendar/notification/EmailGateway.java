package com.acme.appointmentcalendar.notification;



public interface EmailGateway { void send(String to,String subject,String body); }

