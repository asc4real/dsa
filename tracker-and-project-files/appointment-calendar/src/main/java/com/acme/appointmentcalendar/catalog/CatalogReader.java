package com.acme.appointmentcalendar.catalog;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CatalogReader {
 private final Sql db;public CatalogReader(Sql db){this.db=db;}
 public Map<String,Object> service(UUID org,UUID id){
  var r=db.one("SELECT * FROM bookable_service WHERE organization_id=? AND id=? AND active=true",org,id);
  return r;
 }
 public Map<String,Object> location(UUID org,UUID id){
  return db.one("SELECT * FROM location WHERE organization_id=? AND id=? AND active=true",org,id);
 }
 public record Requirement(String key,int quantity,List<Map<String,Object>> candidates){}
 public List<Requirement> candidates(UUID org,UUID service,UUID location,UUID preferredStaff){
  if(preferredStaff!=null)
   db.one("SELECT id FROM staff_member WHERE organization_id=? AND id=? AND active=true",org,preferredStaff);
  List<Map<String,Object>> requirements=db.many("SELECT * FROM service_resource_requirement WHERE organization_id=? AND service_id=? ORDER BY requirement_key",org,service);
  if(requirements.isEmpty())throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","No resource requirements configured");
  List<Requirement> groups=new ArrayList<>();
  for(var req:requirements){
   var eligible=db.many("""
    SELECT cr.* FROM calendar_resource cr JOIN service_requirement_eligible_resource er
     ON er.organization_id=cr.organization_id AND er.resource_id=cr.id AND er.requirement_id=?
    WHERE cr.organization_id=? AND cr.location_id=? AND cr.active=true AND cr.type=?
    ORDER BY cr.id LIMIT 128
    """,req.get("id"),org,location,req.get("resourceType"));
   if(preferredStaff!=null && "STAFF_MEMBER".equals(req.get("resourceType")))
    eligible=eligible.stream().filter(x->preferredStaff.equals(x.get("staffId"))).toList();
   int quantity=((Number)req.get("quantity")).intValue();
   if(eligible.size()<quantity)throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","Not enough eligible resources");
   groups.add(new Requirement((String)req.get("requirementKey"),quantity,eligible));
  }
  return groups;
 }
 public List<Map<String,Object>> resources(UUID org,UUID service,UUID location,UUID preferredStaff){
  if(preferredStaff!=null){
    var staff=db.one("SELECT id FROM staff_member WHERE organization_id=? AND id=? AND active=true",org,preferredStaff);
  }
  List<Map<String,Object>> requirements=db.many("SELECT * FROM service_resource_requirement WHERE organization_id=? AND service_id=? ORDER BY requirement_key",org,service);
  if(requirements.isEmpty()) throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","Service resource requirements have not been configured");
  List<Map<String,Object>> selected=new ArrayList<>();Set<UUID> used=new HashSet<>();
  for(var req:requirements){
   UUID reqId=(UUID)req.get("id");int quantity=((Number)req.get("quantity")).intValue();
   List<Map<String,Object>> eligible=db.many("""
    SELECT cr.* FROM calendar_resource cr
    JOIN service_requirement_eligible_resource eligible ON eligible.organization_id=cr.organization_id
      AND eligible.resource_id=cr.id AND eligible.requirement_id=?
    WHERE cr.organization_id=? AND cr.location_id=? AND cr.active=true AND cr.type=?
    ORDER BY cr.id
    """,reqId,org,location,req.get("resourceType"));
   int chosen=0;
   for(var candidate:eligible){
    if(preferredStaff!=null && "STAFF_MEMBER".equals(req.get("resourceType")) && !preferredStaff.equals(candidate.get("staffId")))continue;
    UUID id=(UUID)candidate.get("id");if(used.add(id)){selected.add(candidate);chosen++;if(chosen==quantity)break;}
   }
   if(chosen<quantity)throw new ApiFailure(422,"RESOURCE_NOT_ELIGIBLE","No eligible resource combination exists");
  }
  return selected;
 }
 public void serviceAtLocation(UUID org,UUID svc,UUID loc){
  if(db.optional("SELECT 1 AS present FROM service_location WHERE organization_id=? AND service_id=? AND location_id=?",org,svc,loc).isEmpty())
   throw new ApiFailure(422,"SERVICE_NOT_BOOKABLE","Service is not offered at this location");
 }
}

