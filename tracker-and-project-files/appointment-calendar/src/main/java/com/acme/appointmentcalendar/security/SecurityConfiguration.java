package com.acme.appointmentcalendar.security;



import org.springframework.context.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
@Configuration
public class SecurityConfiguration {
 @Bean SecurityFilterChain httpChain(HttpSecurity http) throws Exception {
  return http.cors(org.springframework.security.config.Customizer.withDefaults())
   .csrf(csrf->csrf.disable())
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(auth->auth
     .requestMatchers("/actuator/health","/actuator/health/**").permitAll()
     .anyRequest().authenticated())
   .oauth2ResourceServer(oauth->oauth.jwt(jwt->{})).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(@Value("${APP_ALLOWED_ORIGINS:}") String origins){
  UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
  if(origins==null||origins.isBlank())return source; // no cross-origin access by default
  CorsConfiguration config=new CorsConfiguration();
  config.setAllowedOrigins(Arrays.stream(origins.split(",")).map(String::trim).filter(x->!x.isBlank()&&!x.equals("*")).toList());
  config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
  config.setAllowedHeaders(List.of("Authorization","Content-Type","Idempotency-Key","If-Match","X-Correlation-ID"));
  config.setExposedHeaders(List.of("ETag","Location","X-Correlation-ID"));
  config.setAllowCredentials(false);
  source.registerCorsConfiguration("/api/**",config);return source;
 }
 @Bean JwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer,
    @Value("${spring.security.oauth2.resourceserver.jwt.audiences}") String audience) {
  NimbusJwtDecoder decoder=JwtDecoders.fromIssuerLocation(issuer);
  OAuth2TokenValidator<Jwt> base=JwtValidators.createDefaultWithIssuer(issuer);
  OAuth2TokenValidator<Jwt> aud=token->token.getAudience().contains(audience) ?
      OAuth2TokenValidatorResult.success() : OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token","Invalid audience",null));
  decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(base,aud));return decoder;
 }
}

