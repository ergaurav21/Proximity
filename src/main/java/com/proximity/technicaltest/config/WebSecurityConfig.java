package com.proximity.technicaltest.config;

import com.proximity.technicaltest.filter.JwtRequestFilter;
import com.proximity.technicaltest.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

   private final JwtUserDetailService jwtUserDetailsService;

   private final JwtRequestFilter jwtRequestFilter;

  @Autowired
  public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtUserDetailService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter){
     this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
     this.jwtUserDetailsService = jwtUserDetailsService;
     this.jwtRequestFilter = jwtRequestFilter;
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(jwtUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Override
  protected void configure(final HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/register","/authenticate", "/v2/api-docs", "/swagger-ui.*")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/subjects")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PUT, "/subjects/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.DELETE, "/subjects/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.GET, "/subjects")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/tags")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PUT, "/tags/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.DELETE, "/tags/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.GET, "/courses")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/courses")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PUT, "/courses/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PATCH, "/courses/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.DELETE, "/courses/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.GET, "/lessons")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/lessons")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PUT, "/lessons/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PATCH, "/lessons/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.DELETE, "/lessons/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.GET, "/videos")
        .authenticated()
        .antMatchers(HttpMethod.POST, "/videos")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PUT, "/videos/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.PATCH, "/videos/**")
        .hasRole("INSTRUCTOR")
        .antMatchers(HttpMethod.DELETE, "/videos/**")
        .hasRole("INSTRUCTOR")
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");

  }

}
