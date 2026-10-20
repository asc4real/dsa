package com.acme.appointmentcalendar.platform.api;

import com.acme.appointmentcalendar.OperationRouter;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.net.URI;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
@RestController
public class PlatformApiController {
 private final OperationRouter operations;
 public PlatformApiController(OperationRouter operations){this.operations=operations;}
 @RequestMapping(path="/api/v1/organizations/{orgId}/audit-events",method=RequestMethod.GET)
 public ResponseEntity<?> listAuditEvents(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listAuditEvents",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }
 @RequestMapping(path="/api/v1/platform/organizations",method=RequestMethod.GET)
 public ResponseEntity<?> listPlatformOrganizations(
    @PathVariable Map<String,String> path,
    @RequestBody(required=false) Map<String,Object> body,
    @RequestHeader(name="Idempotency-Key",required=false) String idem,
    @RequestHeader(name="If-Match",required=false) String match,
    @RequestParam(name="limit",defaultValue="20") int limit,
    HttpServletRequest servletRequest,
    @RequestParam Map<String,String> query) {
  Object result=operations.route("listPlatformOrganizations",path,body,match,idem,limit,query);
  if(result instanceof Map<?,?> value && value.get("version")!=null) return ResponseEntity.ok().eTag("\"v"+value.get("version")+"\"").body(result);
  return ResponseEntity.ok(result);
 }

}
