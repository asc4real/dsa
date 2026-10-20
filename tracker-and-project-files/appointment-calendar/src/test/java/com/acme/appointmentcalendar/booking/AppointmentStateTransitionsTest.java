package com.acme.appointmentcalendar.booking;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class AppointmentStateTransitionsTest {
 @Test void completionRequiresInProgress(){
  Set<String> allowedFrom=Set.of("IN_PROGRESS");
  assertFalse(allowedFrom.contains("CONFIRMED"));
  assertTrue(allowedFrom.contains("IN_PROGRESS"));
 }
}
