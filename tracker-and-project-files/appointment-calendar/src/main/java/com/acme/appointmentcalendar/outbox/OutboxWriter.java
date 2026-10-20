package com.acme.appointmentcalendar.outbox;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
@Service
public class OutboxWriter {
 private final Sql db;private final ObjectMapper mapper;
 public OutboxWriter(Sql db,ObjectMapper mapper){this.db=db;this.mapper=mapper;}
 public void record(UUID org,String eventType,UUID aggregateId,Object payload){
  try {db.update("INSERT INTO outbox_event(id,organization_id,event_type,aggregate_id,payload) VALUES(?,?,?,?,CAST(? AS jsonb))",
    UUID.randomUUID(),org,eventType,aggregateId,mapper.writeValueAsString(payload));}
  catch(com.fasterxml.jackson.core.JsonProcessingException ex){throw new IllegalStateException(ex);}
 }
}

