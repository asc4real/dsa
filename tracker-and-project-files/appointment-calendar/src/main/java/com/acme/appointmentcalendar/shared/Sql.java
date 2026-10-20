package com.acme.appointmentcalendar.shared;



import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import java.util.*;
import java.time.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
@Component
public class Sql {
 private final JdbcClient jdbc;
 public Sql(JdbcClient jdbc){this.jdbc=jdbc;}
 public JdbcClient jdbc(){return jdbc;}
 public Map<String,Object> one(String sql,Object... args) {
  return jdbc.sql(sql).params(convert(args)).query((rs,n)->row(rs)).optional().orElseThrow(ApiFailure::notFound);
 }
 public Optional<Map<String,Object>> optional(String sql,Object... args) {
  return jdbc.sql(sql).params(convert(args)).query((rs,n)->row(rs)).optional();
 }
 public List<Map<String,Object>> many(String sql,Object... args) {return jdbc.sql(sql).params(convert(args)).query((rs,n)->row(rs)).list();}
 public int update(String sql,Object... args){return jdbc.sql(sql).params(convert(args)).update();}
 private Object[] convert(Object[] values){
   Object[] converted=new Object[values.length];
   for(int i=0;i<values.length;i++) converted[i]=values[i] instanceof Instant instant?Timestamp.from(instant):values[i];
   return converted;
 }
 private Map<String,Object> row(java.sql.ResultSet rs) throws java.sql.SQLException {
  Map<String,Object> result=new LinkedHashMap<>(); var md=rs.getMetaData();
  for(int i=1;i<=md.getColumnCount();i++) {
   String key=md.getColumnLabel(i), k=camel(key);Object value=rs.getObject(i);
   if(value instanceof Timestamp t) value=t.toInstant();
   if(value instanceof OffsetDateTime o) value=o.toInstant();
   if(value instanceof java.sql.Date d) value=d.toLocalDate();
   if(value instanceof java.sql.Time t) value=t.toLocalTime();
   result.put(k,value);
  }
  return result;
 }
 private String camel(String s){ StringBuilder b=new StringBuilder(); boolean cap=false;
  for(char c:s.toCharArray()) {if(c=='_'){cap=true;continue;}b.append(cap?Character.toUpperCase(c):c);cap=false;}return b.toString(); }
}

