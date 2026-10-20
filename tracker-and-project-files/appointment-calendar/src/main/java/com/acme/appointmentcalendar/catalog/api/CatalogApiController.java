package com.acme.appointmentcalendar.catalog.api;

import com.acme.appointmentcalendar.OperationRouter;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.net.URI;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
@RestController
public class CatalogApiController {
 private final OperationRouter operations;
 public CatalogApiController(OperationRouter operations){this.operations=operations;}
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources",method=RequestMethod.GET)
 public ResponseEntity<?> listResources(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listResources",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources",method=RequestMethod.POST)
 public ResponseEntity<?> createResource(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createResource",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}",method=RequestMethod.GET)
 public ResponseEntity<?> getResource(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getResource",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}",method=RequestMethod.PATCH)
 public ResponseEntity<?> updateResource(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("updateResource",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/resources/{resourceId}/deactivate",method=RequestMethod.POST)
 public ResponseEntity<?> deactivateResource(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deactivateResource",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services",method=RequestMethod.GET)
 public ResponseEntity<?> listServices(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listServices",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services",method=RequestMethod.POST)
 public ResponseEntity<?> createService(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("createService",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("id")!=null) return ResponseEntity.created(URI.create(servletRequest.getRequestURI()+"/"+value.get("id"))).body(result);
  return ResponseEntity.status(201).body(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services/{serviceId}",method=RequestMethod.GET)
 public ResponseEntity<?> getService(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getService",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services/{serviceId}",method=RequestMethod.PATCH)
 public ResponseEntity<?> updateService(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("updateService",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services/{serviceId}/deactivate",method=RequestMethod.POST)
 public ResponseEntity<?> deactivateService(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("deactivateService",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services/{serviceId}/resource-requirements",method=RequestMethod.GET)
 public ResponseEntity<?> getServiceResourceRequirements(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("getServiceResourceRequirements",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/organizations/{orgId}/services/{serviceId}/resource-requirements",method=RequestMethod.PUT)
 public ResponseEntity<?> replaceServiceResourceRequirements(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("replaceServiceResourceRequirements",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }

}
