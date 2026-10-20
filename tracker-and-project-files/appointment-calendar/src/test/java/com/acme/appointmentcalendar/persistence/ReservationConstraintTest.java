package com.acme.appointmentcalendar.persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationConstraintTest {
 private PostgreSQLContainer pg; private JdbcTemplate db;
 private final UUID org=UUID.randomUUID(),resource=UUID.randomUUID(),user=UUID.randomUUID(),location=UUID.randomUUID(),service=UUID.randomUUID();
 @BeforeAll void start(){
  pg=new PostgreSQLContainer<>("postgres:18-alpine").withDatabaseName("appointments").withUsername("appointments").withPassword("local-test-only");
  pg.start();
  Flyway.configure().dataSource(pg.getJdbcUrl(),pg.getUsername(),pg.getPassword()).locations("classpath:db/migration").load().migrate();
  db=new JdbcTemplate(new DriverManagerDataSource(pg.getJdbcUrl(),pg.getUsername(),pg.getPassword()));
  db.update("INSERT INTO user_profile(id,issuer,subject) VALUES (?, 'test','user')",user);
  db.update("INSERT INTO organization(id,name) VALUES (?,'Test')",org);
  db.update("INSERT INTO location(id,organization_id,name,zone_id) VALUES (?,?,'Location','UTC')",location,org);
  db.update("INSERT INTO bookable_service(id,organization_id,name,duration_minutes) VALUES (?,?,'Test',30)",service,org);
  db.update("INSERT INTO calendar_resource(id,organization_id,name,type,location_id) VALUES (?,?,'Room','ROOM',?)",resource,org,location);
 }
 @AfterAll void stop(){if(pg!=null)pg.stop();}
 private void reservation(java.time.Instant start,java.time.Instant end){
  db.update("INSERT INTO resource_reservation(id,organization_id,resource_id,appointment_id,reserved_start_at,reserved_end_at) SELECT ?,?,?,id,?,? FROM appointment WHERE organization_id=? LIMIT 1",UUID.randomUUID(),org,resource,java.sql.Timestamp.from(start),java.sql.Timestamp.from(end),org);
 }
 @Test void oneHundredDistinctAttemptsOnlyOneCanReserve() throws Exception {
  UUID appointment=UUID.randomUUID();
  db.update("INSERT INTO appointment(id,organization_id,service_id,location_id,customer_user_profile_id,start_at,end_at,service_name_snapshot,duration_minutes_snapshot) VALUES(?,?,?,?,?,?,?,?,?)",
    appointment,org,service,location,user,java.sql.Timestamp.from(java.time.Instant.parse("2027-01-01T10:00:00Z")),java.sql.Timestamp.from(java.time.Instant.parse("2027-01-01T10:30:00Z")),"Test",30);
  ExecutorService executor=Executors.newFixedThreadPool(24);
  CountDownLatch latch=new CountDownLatch(1);
  List<Future<Boolean>> futures=new ArrayList<>();
  for(int i=0;i<100;i++)futures.add(executor.submit(()->{
   latch.await();try{
    db.update("INSERT INTO resource_reservation(id,organization_id,resource_id,appointment_id,reserved_start_at,reserved_end_at) VALUES (?,?,?,?,?,?)",UUID.randomUUID(),org,resource,appointment,
      java.sql.Timestamp.from(java.time.Instant.parse("2027-01-01T10:00:00Z")),java.sql.Timestamp.from(java.time.Instant.parse("2027-01-01T10:30:00Z")));
    return true;
   }catch(org.springframework.dao.DataAccessException rejected){return false;}
  }));
  latch.countDown();int successes=0;for(var f:futures)if(f.get(90,TimeUnit.SECONDS))successes++;
  executor.shutdownNow();assertEquals(1,successes);
  assertEquals(1,db.queryForObject("SELECT COUNT(*) FROM resource_reservation WHERE organization_id=? AND state='ACTIVE'",Integer.class,org));
 }
 @Test void adjacentIntervalsDoNotOverlap(){
  // Separate interval allocation to verify '[)' exclusion boundaries.
  var a=java.sql.Timestamp.from(java.time.Instant.parse("2027-02-01T10:00:00Z"));
  var b=java.sql.Timestamp.from(java.time.Instant.parse("2027-02-01T10:30:00Z"));
  var c=java.sql.Timestamp.from(java.time.Instant.parse("2027-02-01T11:00:00Z"));
  assertNotNull(db.queryForObject("SELECT (tstzrange(?::timestamptz,?::timestamptz,'[)') && tstzrange(?::timestamptz,?::timestamptz,'[)'))",Boolean.class,a,b,b,c));
  assertFalse(db.queryForObject("SELECT (tstzrange(?::timestamptz,?::timestamptz,'[)') && tstzrange(?::timestamptz,?::timestamptz,'[)'))",Boolean.class,a,b,b,c));
 }
}
