package com.acme.appointmentcalendar.booking;

import com.acme.appointmentcalendar.shared.*;
import com.acme.appointmentcalendar.security.*;
import com.acme.appointmentcalendar.catalog.*;
import com.acme.appointmentcalendar.availability.*;
import com.acme.appointmentcalendar.reservation.*;
import com.acme.appointmentcalendar.idempotency.*;
import com.acme.appointmentcalendar.audit.*;
import com.acme.appointmentcalendar.outbox.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.flywaydb.core.Flyway;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingWorkflowIntegrationTest {
 private PostgreSQLContainer pg; private Sql db; private TransactionTemplate transactions;
 private BookingService booking;private UUID org,room,service,location,user;
 private Instant availableStart;
 private final String issuer="https://test.identity.example";

 @BeforeAll void setup(){
  pg=new PostgreSQLContainer("postgres:18-alpine").withDatabaseName("appointments")
    .withUsername("appointments").withPassword("local-test-only");
  pg.start();
  Flyway.configure().dataSource(pg.getJdbcUrl(),pg.getUsername(),pg.getPassword())
    .locations("classpath:db/migration").load().migrate();
  var ds=new DriverManagerDataSource(pg.getJdbcUrl(),pg.getUsername(),pg.getPassword());
  db=new Sql(JdbcClient.create(ds));transactions=new TransactionTemplate(new DataSourceTransactionManager(ds));
  var actor=new ActorResolver(db);var auth=new Permissions(db,actor);
  ObjectMapper json=new ObjectMapper().registerModule(new JavaTimeModule());
  var catalog=new CatalogReader(db);var availability=new AvailabilityPolicy(db,catalog);
  booking=new BookingService(db,auth,catalog,availability,new ReservationPort(db),new CandidateResourceAllocator(catalog,new ReservationPort(db),availability),
    new IdempotencyCoordinator(db,json),new AuditWriter(db),new OutboxWriter(db,json),
    new CursorCodec("TEST_CURSOR_HMAC_KEY_LONGER_THAN_32_BYTES"),300);
  authenticate("alice");
  user=transactions.execute(tx->auth.actor().userId());
  org=UUID.randomUUID();location=UUID.randomUUID();service=UUID.randomUUID();room=UUID.randomUUID();
  availableStart=LocalDate.now(ZoneOffset.UTC).plusDays(3).atTime(10,0).toInstant(ZoneOffset.UTC);
  transactions.executeWithoutResult(tx->{
   db.update("INSERT INTO organization(id,name,default_zone_id) VALUES (?,'Test tenant','UTC')",org);
   db.update("INSERT INTO organization_membership(id,organization_id,user_profile_id,role) VALUES (?,?,?,'CUSTOMER')",UUID.randomUUID(),org,user);
   db.update("INSERT INTO location(id,organization_id,name,zone_id) VALUES (?,?,'Main','UTC')",location,org);
   db.update("INSERT INTO bookable_service(id,organization_id,name,duration_minutes) VALUES (?,?,'Consultation',30)",service,org);
   db.update("INSERT INTO service_location(organization_id,service_id,location_id) VALUES (?,?,?)",org,service,location);
   db.update("INSERT INTO calendar_resource(id,organization_id,name,type,location_id) VALUES (?,?,'Room A','ROOM',?)",room,org,location);
   UUID req=UUID.randomUUID();
   db.update("INSERT INTO service_resource_requirement(id,organization_id,service_id,requirement_key,resource_type,quantity) VALUES (?,?,?,'room','ROOM',1)",req,org,service);
   db.update("INSERT INTO service_requirement_eligible_resource(organization_id,requirement_id,resource_id) VALUES (?,?,?)",org,req,room);
   db.update("INSERT INTO availability_rule(id,organization_id,resource_id,day_of_week,starts_at,ends_at) VALUES (?,?,?,?,?,?)",
    UUID.randomUUID(),org,room,availableStart.atZone(ZoneOffset.UTC).getDayOfWeek().getValue(),LocalTime.of(9,0),LocalTime.of(17,0));
  });
 }
 @AfterAll void teardown(){SecurityContextHolder.clearContext();if(pg!=null)pg.stop();}
 private void authenticate(String subject){
  var jwt=Jwt.withTokenValue("test-only-token").header("alg","RS256")
    .issuer(issuer).subject(subject).audience(List.of("appointment-calendar-api"))
    .issuedAt(Instant.now()).expiresAt(Instant.now().plusSeconds(3600)).build();
  SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
 }
 private Map<String,Object> request(){return Map.of("serviceId",service.toString(),"locationId",location.toString(),"startAt",availableStart.toString());}
 @Test @Order(1) void creationAndRetryAreOneLogicalOperation(){
  authenticate("alice");String key=UUID.randomUUID().toString();
  Map<String,Object> first=transactions.execute(t->booking.createHold(org,key,request()));
  Map<String,Object> retry=transactions.execute(t->booking.createHold(org,key,request()));
  assertEquals(first.get("id"),retry.get("id"));
  assertEquals(1,db.one("SELECT count(*) AS n FROM resource_reservation WHERE organization_id=? AND state='ACTIVE'",org).get("n") instanceof Long n?n.intValue():-1);
 }
 @Test @Order(2) void differentIdempotencyKeyCannotDoubleBook() {
  authenticate("alice");
  assertThrows(RuntimeException.class,()->transactions.execute(t->booking.createHold(org,UUID.randomUUID().toString(),request())));
 }
 @Test @Order(3) void confirmRescheduleAndCancelAreAtomic(){
  authenticate("alice");
  UUID hold=(UUID)db.one("SELECT id FROM booking_hold WHERE organization_id=? AND status='ACTIVE' LIMIT 1",org).get("id");
  Map<String,Object> confirmed=transactions.execute(t->booking.confirmHold(org,hold,UUID.randomUUID().toString()));
  UUID appointment=(UUID)confirmed.get("id");
  assertEquals("CONFIRMED",confirmed.get("status"));
  assertThrows(ApiFailure.class,()->transactions.execute(t->booking.command(org,appointment,"reschedule","\"v0\"",UUID.randomUUID().toString(),
    Map.of("startAt",availableStart.plusSeconds(3600).toString()))));
  Map<String,Object> rescheduled=transactions.execute(t->booking.command(org,appointment,"reschedule","\"v1\"",UUID.randomUUID().toString(),
    Map.of("startAt",availableStart.plusSeconds(3600).toString())));
  assertEquals(2L,((Number)rescheduled.get("version")).longValue());
  Map<String,Object> cancelled=transactions.execute(t->booking.command(org,appointment,"cancel","\"v2\"",UUID.randomUUID().toString(),
    Map.of("reason","Customer cancellation")));
  assertEquals("CANCELLED",cancelled.get("status"));
  assertEquals(0L,((Number)db.one("SELECT count(*) AS n FROM resource_reservation WHERE organization_id=? AND appointment_id=? AND state='ACTIVE'",org,appointment).get("n")).longValue());
 }
 @Test @Order(5) void cannotAccessDifferentTenant(){
  authenticate("alice");UUID otherOrg=UUID.randomUUID();
  assertThrows(ApiFailure.class,()->transactions.execute(t->booking.getHold(otherOrg,UUID.randomUUID())));
 }
}
