package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.SubjectModel;
import com.proximity.technicaltest.model.SubjectRequest;
import com.proximity.technicaltest.model.SubjectUpsertRequest;
import com.proximity.technicaltest.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public  SubjectController(final SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @ApiOperation("Create a Subject")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/subjects")
    public ResponseEntity create(@RequestBody @Valid final SubjectRequest subjectRequest) {
        SubjectController.log.info("Request : {}", subjectRequest);
        subjectService.create(subjectRequest.toSubjectEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Update a Subject")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PutMapping("/subjects/{subject_name}")
    public ResponseEntity update(@PathVariable("subject_name") String subjectName, @RequestBody @Valid final SubjectUpsertRequest subjectRequest) {
        SubjectController.log.info("Request : {}", subjectRequest);
        subjectService.update(subjectRequest,subjectName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Delete a Subject")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/subjects/{subject_name}")
    public ResponseEntity delete(@PathVariable("subject_name") String subjectName) {
        subjectService.delete(subjectName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Get all Subjects")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Accepted")
    })
    @GetMapping("/subjects")
    public ResponseEntity get() {
       List<SubjectModel> subjects = subjectService.getAllSubjects();
        return new ResponseEntity(subjects, HttpStatus.OK);
    }




}
