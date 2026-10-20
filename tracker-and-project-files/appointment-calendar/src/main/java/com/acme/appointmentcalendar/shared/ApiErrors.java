package com.acme.appointmentcalendar.shared;



import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataAccessException;
import java.net.URI;
import java.util.*;
@RestControllerAdvice
public class ApiErrors {
 @ExceptionHandler(ApiFailure.class)
 ResponseEntity<ProblemDetail> domain(ApiFailure e) {return make(e.status(),e.code(),e.getMessage());}
 @ExceptionHandler({HttpMessageNotReadableException.class,MethodArgumentTypeMismatchException.class,MethodArgumentNotValidException.class,IllegalArgumentException.class})
 ResponseEntity<ProblemDetail> invalid(Exception e){return make(400,"INVALID_REQUEST","Invalid request data");}
 @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
 ResponseEntity<ProblemDetail> duplicate(Exception e){return make(409,"CONFLICT","A conflicting record exists");}
 @ExceptionHandler(DataAccessException.class)
 ResponseEntity<ProblemDetail> database(DataAccessException e) {
  Throwable t=e; while(t!=null) {
   if(t instanceof java.sql.SQLException s && "23P01".equals(s.getSQLState()))
    return make(409,"APPOINTMENT_SLOT_CONFLICT","The requested appointment time is no longer available");
   if(t instanceof java.sql.SQLException s && "23505".equals(s.getSQLState()))
    return make(409,"CONFLICT","A conflicting record exists");
   t=t.getCause();
  }
  return make(503,"DEPENDENCY_UNAVAILABLE","The service is temporarily unavailable");
 }
 @ExceptionHandler(Exception.class)
 ResponseEntity<ProblemDetail> unknown(Exception e){return make(500,"INTERNAL_ERROR","Unexpected server failure");}
 private ResponseEntity<ProblemDetail> make(int status,String code,String detail){
  ProblemDetail p=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status),detail);
  p.setTitle(code.replace('_',' '));p.setType(URI.create("https://api.example.invalid/problems/"+code.toLowerCase().replace('_','-')));
  p.setProperty("errorCode",code);
  return ResponseEntity.status(status).contentType(MediaType.valueOf("application/problem+json")).body(p);
 }
}

