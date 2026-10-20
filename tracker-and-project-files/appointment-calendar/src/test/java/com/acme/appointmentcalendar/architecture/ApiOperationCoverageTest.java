package com.acme.appointmentcalendar.architecture;
import org.junit.jupiter.api.Test;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;
class ApiOperationCoverageTest {
 @Test void fullContractHas67Operations() throws Exception {
  String content=Files.readString(Path.of("openapi/appointment-calendar.yaml"));
  long count=content.lines().filter(line->line.matches("\\s*operationId:.*")).count();
  assertEquals(67,count);
 }
}
