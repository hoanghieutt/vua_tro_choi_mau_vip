package com.example.shopapp.config.authentication;
import com.example.shopapp.Responses.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {

  @Value("${application.i18n.locale.language:en}")
  String language;

  private final AuthenticationFilter authenticationFilter;
  private final AuthenticationProvider authenticationProvider;
  private final MessageSource messageSource;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // CSRF disabled, consider enabling it if you handle non-REST requests
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(authenticationEntryPoint())
        );

    return http.build();
  }

  private static final String[] AUTH_WHITELIST = {
      "/v3/api-docs/**",
      "/v3/api-docs.yaml",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/api/v1/auth/**",
      "/api/v1/**/public/**"
  };

  private AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      String error = "com.spring.jwt_spring_security.exception.UnauthorizedException";
      String message = messageSource.getMessage(error, null, new Locale(language));
      ErrorResponse errorResponse = ErrorResponse.of(
          HttpServletResponse.SC_UNAUTHORIZED, error, message
      );
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
      response.getOutputStream().flush();
    };
  }
}