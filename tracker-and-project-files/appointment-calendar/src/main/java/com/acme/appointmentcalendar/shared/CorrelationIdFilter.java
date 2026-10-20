package com.acme.appointmentcalendar.shared;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

@Component
public final class CorrelationIdFilter extends OncePerRequestFilter {
 @Override
 protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
   throws ServletException,IOException {
  String candidate=request.getHeader("X-Correlation-ID");
  String id=candidate!=null&&candidate.matches("[a-zA-Z0-9_-]{8,80}")?candidate:"req-"+UUID.randomUUID();
  MDC.put("correlationId",id);
  response.setHeader("X-Correlation-ID",id);
  try{chain.doFilter(request,response);}finally{MDC.remove("correlationId");}
 }
}
