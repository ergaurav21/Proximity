package com.proximity.technicaltest.controller;


import com.proximity.technicaltest.model.JwtRequest;
import com.proximity.technicaltest.model.JwtResponse;
import com.proximity.technicaltest.service.JwtTokenService;
import com.proximity.technicaltest.service.JwtUserDetailService;
import com.proximity.technicaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
public class JwtAuthenticationController {

    private final JwtTokenService jwtTokenService;
    private final JwtUserDetailService userDetailsService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public JwtAuthenticationController (JwtTokenService jwtTokenService, JwtUserDetailService userDetailsService, AuthenticationManager authenticationManager){
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) throws Exception {
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


}