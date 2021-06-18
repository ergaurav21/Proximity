package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.RegistrationRequest;
import com.proximity.technicaltest.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
public class RegistrationController {

    private final UserService registrationService;

    @Autowired
    public  RegistrationController(final UserService registrationService){
        this.registrationService = registrationService;
    }

    @ApiOperation("Register a User")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid final RegistrationRequest registrationRequest) {
        RegistrationController.log.info("Request : {}", registrationRequest);
        registrationService.save(registrationRequest.toUserEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
