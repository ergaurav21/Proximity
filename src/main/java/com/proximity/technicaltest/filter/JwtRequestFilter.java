package com.proximity.technicaltest.filter;

import com.proximity.technicaltest.service.JwtTokenService;
import com.proximity.technicaltest.service.JwtUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUserDetailService jwtUserDetailsService;
  private final JwtTokenService jwtTokenService;

  @Autowired
  public JwtRequestFilter(
          final JwtUserDetailService jwtUserDetailsService, final JwtTokenService jwtTokenService) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtTokenService = jwtTokenService;
  }

  @Override
  protected void doFilterInternal(
          final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenService.getUsernameFromToken(jwtToken);
      } catch (final IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (final ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

      if (jwtTokenService.validateToken(jwtToken, userDetails)) {

        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
      request.setAttribute("userName",username);
    }
    chain.doFilter(request, response);
  }
}
