package com.acme.appointmentcalendar.shared;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;
@Component
public class CursorCodec {
 private final String secret;
 public CursorCodec(@Value("${app.pagination.secret:}") String secret){this.secret=secret;}
 public String encode(String payload){
  verifyConfigured();
  String data=Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
  return data+"."+sign(data);
 }
 public String decode(String cursor){
  verifyConfigured();
  if(cursor==null||cursor.length()>1024)throw ApiFailure.bad("Invalid pagination cursor");
  int dot=cursor.lastIndexOf('.');if(dot<=0)throw ApiFailure.bad("Invalid pagination cursor");
  String body=cursor.substring(0,dot), supplied=cursor.substring(dot+1);
  if(!MessageDigest.isEqual(sign(body).getBytes(StandardCharsets.US_ASCII),supplied.getBytes(StandardCharsets.US_ASCII)))
    throw ApiFailure.bad("Pagination cursor signature mismatch");
  try{return new String(Base64.getUrlDecoder().decode(body),StandardCharsets.UTF_8);}
  catch(IllegalArgumentException e){throw ApiFailure.bad("Invalid pagination cursor encoding");}
 }
 private void verifyConfigured(){if(secret.length()<32)throw new ApiFailure(503,"PAGINATION_NOT_CONFIGURED","Pagination secret has not been configured");}
 private String sign(String data){try{
  Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256"));
  return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
 }catch(Exception e){throw new IllegalStateException("Cannot sign cursor",e);}}
}
