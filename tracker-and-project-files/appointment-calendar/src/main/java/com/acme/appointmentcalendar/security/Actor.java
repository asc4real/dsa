package com.acme.appointmentcalendar.security;



import java.util.UUID;
public record Actor(UUID userId, String issuer, String subject){}

