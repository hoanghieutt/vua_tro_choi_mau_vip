package com.example.shopapp.config.authentication;


import com.example.shopapp.models.User;
import com.example.shopapp.services.impl.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
// xác thực
public class AuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final HandlerExceptionResolver resolver;

  public AuthenticationFilter(
      JwtService jwtService,
      UserDetailsService userDetailsService,
      UserService userService,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
  ) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.resolver = resolver;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    try {

      if (request.getServletPath().contains("/api/v1/auth")) {
        filterChain.doFilter(request, response);
        return;
      }

      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      final String jwt = authHeader.substring(7);

      final String username = jwtService.extractUsername(jwt);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Optional<User> user = userService.findByFullname(username);
        if (jwtService.isTokenValid(jwt, userDetails) && user.isPresent()) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              user.get().getId(),
              userDetails.getAuthorities()
          );
          authToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request)
          );
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);

    } catch (JwtException exception) {
      resolver.resolveException(request, response, null, exception);
    }
  }

}
