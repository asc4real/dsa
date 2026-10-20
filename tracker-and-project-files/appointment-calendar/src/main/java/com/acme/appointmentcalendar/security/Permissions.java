package com.acme.appointmentcalendar.security;



import com.acme.appointmentcalendar.shared.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class Permissions {
 private final Sql db; private final ActorResolver actors;
 public Permissions(Sql db,ActorResolver actors){this.db=db;this.actors=actors;}
 public Actor actor(){return actors.current();}
 public String membership(UUID org){
  Actor a=actor();
  return db.optional("SELECT role FROM organization_membership WHERE organization_id=? AND user_profile_id=? AND status='ACTIVE'",org,a.userId())
   .map(m->m.get("role").toString()).orElseThrow(()->new ApiFailure(404,"ORGANIZATION_NOT_FOUND","Organization not found"));
 }
 public void member(UUID org){membership(org);}
 public void admin(UUID org){String r=membership(org);if(!r.equals("ORGANIZATION_ADMIN"))throw ApiFailure.denied();}
 public void scheduler(UUID org){String r=membership(org);if(!Set.of("SCHEDULER","ORGANIZATION_ADMIN").contains(r))throw ApiFailure.denied();}
 public boolean elevated(UUID org){return Set.of("SCHEDULER","ORGANIZATION_ADMIN").contains(membership(org));}
 public boolean staff(UUID org){return Set.of("STAFF","SCHEDULER","ORGANIZATION_ADMIN").contains(membership(org));}
 public void ownerOrElevated(UUID org,UUID owner){if(!elevated(org)&&!actor().userId().equals(owner))throw ApiFailure.notFound();}
 public void platform(){var token=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
  if(token==null||token.getAuthorities().stream().noneMatch(x->x.getAuthority().equals("SCOPE_platform.admin")))throw ApiFailure.denied(); }
}

