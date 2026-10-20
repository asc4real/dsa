package com.acme.appointmentcalendar.availability;

import com.acme.appointmentcalendar.catalog.CatalogReader;
import com.acme.appointmentcalendar.reservation.ReservationPort;
import com.acme.appointmentcalendar.shared.ApiFailure;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;

@Service
public class CandidateResourceAllocator {
 private final CatalogReader catalog;
 private final ReservationPort reservations;
 private final AvailabilityPolicy policy;
 public CandidateResourceAllocator(CatalogReader catalog,ReservationPort reservations,AvailabilityPolicy policy){
  this.catalog=catalog;this.reservations=reservations;this.policy=policy;
 }
 public List<Map<String,Object>> allocate(UUID org,UUID service,UUID location,Instant start,UUID preferredStaff){
  var svc=catalog.service(org,service);
  int duration=((Number)svc.get("durationMinutes")).intValue();
  int before=((Number)svc.get("bufferBeforeMinutes")).intValue();
  int after=((Number)svc.get("bufferAfterMinutes")).intValue();
  Instant end=start.plusSeconds(duration*60L);
  var groups=catalog.candidates(org,service,location,preferredStaff);
  // Quantity is expanded into individual choices, each requiring a distinct resource.
  List<List<Map<String,Object>>> tasks=new ArrayList<>();
  for(var group:groups) for(int n=0;n<group.quantity();n++) tasks.add(group.candidates());
  if(tasks.size()>16)throw new ApiFailure(422,"RESOURCE_REQUIREMENTS_TOO_COMPLEX","At most 16 resource units per booking");
  List<Map<String,Object>> selected=new ArrayList<>();Set<UUID> used=new HashSet<>();
  int[] visited={0};
  if(!search(org,service,location,start,end,before,after,tasks,0,used,selected,visited))
   throw ApiFailure.conflict("APPOINTMENT_SLOT_CONFLICT","No eligible resource combination is free at this time");
  return List.copyOf(selected);
 }
 private boolean search(UUID org,UUID service,UUID location,Instant start,Instant end,int before,int after,
    List<List<Map<String,Object>>> tasks,int index,Set<UUID> used,List<Map<String,Object>> selected,int[] visited){
  if(index==tasks.size())return true;
  if(visited[0]++>=1024)return false;
  for(var resource:tasks.get(index)){
   UUID id=(UUID)resource.get("id");if(used.contains(id))continue;
   if(reservations.occupied(org,List.of(resource),start,end,before,after))continue;
   try{policy.validate(org,service,location,start,List.of(resource));}
   catch(ApiFailure rejected){continue;}
   used.add(id);selected.add(resource);
   if(search(org,service,location,start,end,before,after,tasks,index+1,used,selected,visited))return true;
   selected.removeLast();used.remove(id);
  }
  return false;
 }
}
