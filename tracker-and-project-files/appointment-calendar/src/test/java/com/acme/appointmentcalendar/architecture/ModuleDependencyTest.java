package com.acme.appointmentcalendar.architecture;
import com.acme.appointmentcalendar.AppointmentCalendarApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
class ModuleDependencyTest {
 @Test void namedModulesMustNotBeCyclic(){
  ApplicationModules.of(AppointmentCalendarApplication.class).verify();
 }
}
