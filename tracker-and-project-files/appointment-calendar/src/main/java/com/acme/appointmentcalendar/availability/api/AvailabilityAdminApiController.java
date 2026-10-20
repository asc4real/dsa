package com.acme.appointmentcalendar.availability.api;

import com.acme.appointmentcalendar.OperationRouter;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.net.URI;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
@RestController
public class AvailabilityAdminApiController {
 private final OperationRouter operations;
 public AvailabilityAdminApiController(OperationRouter operations){this.operations=operations;}
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules",method=RequestMethod.GET)
 public ResponseEntity<?> listAvailabilityRules(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listAvailabilityRules",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules",method=RequestMethod.POST)
 public ResponseEntity<?> createAvailabilityRule(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createAvailabilityRule",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules/{ruleId}",method=RequestMethod.PATCH)
 public ResponseEntity<?> updateAvailabilityRule(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("updateAvailabilityRule",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-rules/{ruleId}",method=RequestMethod.DELETE)
 public ResponseEntity<?> deleteAvailabilityRule(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deleteAvailabilityRule",path,body,match,idem,limit,query);
  return ResponseEntity.noContent().build();
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions",method=RequestMethod.GET)
 public ResponseEntity<?> listAvailabilityExceptions(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listAvailabilityExceptions",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions",method=RequestMethod.POST)
 public ResponseEntity<?> createAvailabilityException(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createAvailabilityException",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/availability-exceptions/{exceptionId}",method=RequestMethod.DELETE)
 public ResponseEntity<?> deleteAvailabilityException(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deleteAvailabilityException",path,body,match,idem,limit,query);
  return ResponseEntity.noContent().build();
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}/time-off",method=RequestMethod.GET)
 public ResponseEntity<?> listStaffTimeOff(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listStaffTimeOff",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}/time-off",method=RequestMethod.POST)
 public ResponseEntity<?> createStaffTimeOff(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createStaffTimeOff",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}/time-off/{timeOffId}/cancel",method=RequestMethod.POST)
 public ResponseEntity<?> cancelStaffTimeOff(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("cancelStaffTimeOff",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }

}
