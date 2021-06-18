package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.CourseRequest;
import com.proximity.technicaltest.model.CoursesModel;
import com.proximity.technicaltest.model.CoursesUpsertRequest;
import com.proximity.technicaltest.service.CourseService;
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
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController( CourseService courseService){
        this.courseService = courseService;
    }

    @ApiOperation("Create a Course")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/courses")
    public ResponseEntity create(@RequestBody @Valid final CourseRequest courseRequest) {
        CourseController.log.info("Request : {}", courseRequest);
        courseService.create(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Update a Course")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PutMapping("/courses/{course_name}")
    public ResponseEntity update(@PathVariable("course_name") String courseName, @RequestBody @Valid final CoursesUpsertRequest coursesUpsertRequest) {
        CourseController.log.info("Request : {}", coursesUpsertRequest);
        courseService.update(coursesUpsertRequest,courseName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Update  Course Subjects")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PatchMapping("/courses/{course_name}")
    public ResponseEntity updateCourseSubjects(@PathVariable("course_name") String courseName, @RequestBody @Valid final CoursesUpsertRequest coursesUpsertRequest) {
        CourseController.log.info("Request : {}", coursesUpsertRequest);
        courseService.updateCourseSubjects(coursesUpsertRequest,courseName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Delete a Course")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/courses/{course_name}")
    public ResponseEntity delete(@PathVariable("course_name") String courseName) {

        courseService.delete(courseName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Get all courses")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Accepted")
    })
    @GetMapping("/courses")
    public ResponseEntity get(@RequestParam(value = "subject", required = false) String subject) {

        List<CoursesModel> tags = courseService.getAllActiveBySubjects(subject);
        return new ResponseEntity(tags, HttpStatus.OK);
    }
}
