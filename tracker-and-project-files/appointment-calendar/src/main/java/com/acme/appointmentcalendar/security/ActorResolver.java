package com.acme.appointmentcalendar.security;



import com.acme.appointmentcalendar.shared.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ActorResolver {
 private final Sql db;
 public ActorResolver(Sql db){this.db=db;}
 public Actor current(){
  if(!(SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken authentication))
   throw new ApiFailure(401,"AUTHENTICATION_REQUIRED","Valid access token required");
  var jwt=authentication.getToken();
  String iss=jwt.getIssuer()==null?"":jwt.getIssuer().toString();
  if(iss.isBlank()||jwt.getSubject()==null) throw new ApiFailure(401,"AUTHENTICATION_REQUIRED","Invalid identity claims");
  UUID id=UUID.nameUUIDFromBytes((iss+"\u0000"+jwt.getSubject()).getBytes(java.nio.charset.StandardCharsets.UTF_8));
  // Provision a stable local profile; org membership is never inferred from a JWT.
  db.update("INSERT INTO user_profile(id,issuer,subject) VALUES (?,?,?) ON CONFLICT (issuer,subject) DO NOTHING",id,iss,jwt.getSubject());
  return new Actor(id,iss,jwt.getSubject());
 }
}

