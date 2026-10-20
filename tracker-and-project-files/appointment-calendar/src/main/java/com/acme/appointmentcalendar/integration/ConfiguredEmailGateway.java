package com.acme.appointmentcalendar.integration;



import com.acme.appointmentcalendar.notification.EmailGateway;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
@Component
public class ConfiguredEmailGateway implements EmailGateway {
 private final JavaMailSender sender;private final boolean enabled;private final String from;
 public ConfiguredEmailGateway(JavaMailSender s,@Value("${app.mail.enabled:false}") boolean enabled,@Value("${app.mail.from:no-reply@example.invalid}")String from){
  sender=s;this.enabled=enabled;this.from=from;
 }
 public void send(String to,String subject,String body){
  if(!enabled)throw new IllegalStateException("Email provider not configured; notification remains pending");
  SimpleMailMessage msg=new SimpleMailMessage();msg.setFrom(from);msg.setTo(to);msg.setSubject(subject);msg.setText(body);sender.send(msg);
 }
}

