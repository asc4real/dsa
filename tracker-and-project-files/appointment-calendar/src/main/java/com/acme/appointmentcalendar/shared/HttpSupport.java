package com.acme.appointmentcalendar.shared;



import org.springframework.http.*;
import java.net.URI;
import java.util.*;
public final class HttpSupport {
 private HttpSupport(){}
 public static ResponseEntity<Map<String,Object>> ok(Map<String,Object> body){
  return ResponseEntity.ok().eTag("\"v"+body.getOrDefault("version",1)+"\"").cacheControl(CacheControl.noStore()).body(body);
 }
 public static ResponseEntity<Map<String,Object>> created(String path,Map<String,Object> body){
  return ResponseEntity.created(URI.create(path)).eTag("\"v"+body.getOrDefault("version",1)+"\"").body(body);
 }
 public static void requireMatch(String etag,long version){
  if(etag==null||etag.isBlank()) throw new ApiFailure(428,"PRECONDITION_REQUIRED","If-Match header required");
  if(!etag.equals("\"v"+version+"\"")) throw new ApiFailure(412,"PRECONDITION_FAILED","Resource version has changed");
 }
 public static Map<String,Object> page(List<Map<String,Object>> rows,int limit){
  if(limit<1||limit>100) throw ApiFailure.bad("limit must be 1..100");
  boolean more=rows.size()>limit;
  var data=more?rows.subList(0,limit):rows;
  return Map.of("items",data,"page",new Page(limit,null,more));
 }
 public record Page(int size,String nextCursor,boolean hasMore){}
}

