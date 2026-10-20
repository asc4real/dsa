package com.acme.appointmentcalendar.idempotency;



import com.acme.appointmentcalendar.shared.*;
import com.acme.appointmentcalendar.security.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.Supplier;
@Service
public class IdempotencyCoordinator {
 private final Sql db; private final ObjectMapper mapper;
 public IdempotencyCoordinator(Sql db,ObjectMapper mapper){this.db=db;this.mapper=mapper;}
 @Transactional
 public Map<String,Object> execute(UUID org,Actor actor,String operation,String key,Object body,Supplier<Map<String,Object>> action){
  if(key==null||!key.matches("[A-Za-z0-9][A-Za-z0-9._~-]{7,127}"))
   throw new ApiFailure(400,"IDEMPOTENCY_KEY_REQUIRED","Idempotency-Key must contain 8–128 safe ASCII characters");
  String hash=hash(operation+"|"+serial(body));
  UUID id=UUID.randomUUID();
  int won=db.update("""
   INSERT INTO idempotency_record(id,organization_id,principal_id,operation,idempotency_key,request_hash,state,expires_at)
   VALUES(?,?,?,?,?,?,'PROCESSING',clock_timestamp()+INTERVAL '72 hours')
   ON CONFLICT(organization_id,principal_id,operation,idempotency_key) DO NOTHING
   """,id,org,actor.userId(),operation,key,hash);
  if(won==0){
   Map<String,Object> existing=db.one("""
    SELECT request_hash,state,response_body::text AS response_json FROM idempotency_record
    WHERE organization_id=? AND principal_id=? AND operation=? AND idempotency_key=? FOR UPDATE
    """,org,actor.userId(),operation,key);
   if(!hash.equals(existing.get("requestHash")))throw ApiFailure.conflict("IDEMPOTENCY_KEY_REUSED","This key was used for another request");
   if(!"COMPLETED".equals(existing.get("state")))throw ApiFailure.conflict("IDEMPOTENCY_REQUEST_IN_PROGRESS","Original request is still processing");
   try { return mapper.readValue((String)existing.get("responseJson"),new com.fasterxml.jackson.core.type.TypeReference<Map<String,Object>>(){});}
   catch(Exception ex){throw new ApiFailure(503,"DEPENDENCY_UNAVAILABLE","Stored response could not be decoded");}
  }
  Map<String,Object> result=action.get();
  db.update("UPDATE idempotency_record SET state='COMPLETED',response_status=200,response_body=CAST(? AS jsonb) WHERE id=?",serial(result),id);
  return result;
 }
 private String serial(Object obj){try{return mapper.writeValueAsString(obj);}catch(Exception e){throw ApiFailure.bad("Request serialization failed");}}
 private String hash(String value){try{
  byte[] d=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));return HexFormat.of().formatHex(d);
 }catch(Exception ex){throw new IllegalStateException(ex);}}
}

