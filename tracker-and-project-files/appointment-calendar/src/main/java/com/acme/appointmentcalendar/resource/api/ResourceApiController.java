package com.acme.appointmentcalendar.resource.api;

import com.acme.appointmentcalendar.OperationRouter;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.net.URI;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
@RestController
public class ResourceApiController {
 private final OperationRouter operations;
 public ResourceApiController(OperationRouter operations){this.operations=operations;}
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations",method=RequestMethod.GET)
 public ResponseEntity<?> listLocations(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listLocations",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations",method=RequestMethod.POST)
 public ResponseEntity<?> createLocation(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createLocation",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations/{locationId}",method=RequestMethod.GET)
 public ResponseEntity<?> getLocation(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getLocation",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations/{locationId}",method=RequestMethod.PATCH)
 public ResponseEntity<?> updateLocation(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("updateLocation",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations/{locationId}/deactivate",method=RequestMethod.POST)
 public ResponseEntity<?> deactivateLocation(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deactivateLocation",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff",method=RequestMethod.GET)
 public ResponseEntity<?> listStaff(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listStaff",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff",method=RequestMethod.POST)
 public ResponseEntity<?> createStaffMember(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createStaffMember",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}",method=RequestMethod.GET)
 public ResponseEntity<?> getStaffMember(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getStaffMember",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}",method=RequestMethod.PATCH)
 public ResponseEntity<?> updateStaffMember(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("updateStaffMember",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/staff/{staffId}/deactivate",method=RequestMethod.POST)
 public ResponseEntity<?> deactivateStaffMember(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deactivateStaffMember",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations/{locationId}/opening-hours",method=RequestMethod.GET)
 public ResponseEntity<?> getLocationOpeningHours(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getLocationOpeningHours",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/locations/{locationId}/opening-hours",method=RequestMethod.PUT)
 public ResponseEntity<?> replaceLocationOpeningHours(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("replaceLocationOpeningHours",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }

}
