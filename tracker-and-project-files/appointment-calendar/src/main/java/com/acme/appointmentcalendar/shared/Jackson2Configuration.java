package com.acme.appointmentcalendar.shared;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.*;
@Configuration
public class Jackson2Configuration {
 @Bean ObjectMapper internalJsonMapper(){
  ObjectMapper mapper=new ObjectMapper();
  mapper.registerModule(new JavaTimeModule());
  mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  return mapper;
 }
}

