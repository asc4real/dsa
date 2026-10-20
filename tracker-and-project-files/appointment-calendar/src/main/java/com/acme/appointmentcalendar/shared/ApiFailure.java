package com.acme.appointmentcalendar.shared;



public final class ApiFailure extends RuntimeException {
 private final int status; private final String code;
 public ApiFailure(int status, String code, String detail) { super(detail); this.status=status; this.code=code; }
 public int status() { return status; }
 public String code() { return code; }
 public static ApiFailure bad(String m) {return new ApiFailure(400,"INVALID_REQUEST",m);}
 public static ApiFailure notFound() {return new ApiFailure(404,"RESOURCE_NOT_FOUND","Resource not found");}
 public static ApiFailure conflict(String code,String message) {return new ApiFailure(409,code,message);}
 public static ApiFailure denied() {return new ApiFailure(403,"ACCESS_DENIED","Permission denied");}
}

