package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.LessonModel;
import com.proximity.technicaltest.model.LessonRequest;
import com.proximity.technicaltest.model.LessonUpsertRequest;
import com.proximity.technicaltest.service.LessonService;
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
public class LessonController {

  private final LessonService lessonService;

  @Autowired
  public LessonController(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @ApiOperation("Create a Lesson")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
  })
  @PostMapping("/lessons")
  public ResponseEntity create(@RequestBody @Valid final LessonRequest lessonRequest) {
    LessonController.log.info("Request : {}", lessonRequest);
    lessonService.create(lessonRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation("Update a Lesson")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
  })
  @PutMapping("/lessons/{lesson_name}")
  public ResponseEntity update(
      @PathVariable("lesson_name") String courseName,
      @RequestBody @Valid final LessonUpsertRequest lessonsUpsertRequest) {

    LessonController.log.info("Request : {}", lessonsUpsertRequest);
    lessonService.update(lessonsUpsertRequest, courseName);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @ApiOperation("Update  Lesson Courses")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
  })
  @PatchMapping("/lessons/{lesson_name}")
  public ResponseEntity updateLessonSubjects(
      @PathVariable("lesson_name") String lessonName,
      @RequestBody @Valid final LessonUpsertRequest lessonsUpsertRequest) {
    LessonController.log.info("Request : {}", lessonsUpsertRequest);
    lessonService.updateCourses(lessonsUpsertRequest, lessonName);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @ApiOperation("Delete a Lesson")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
  })
  @DeleteMapping("/lessons/{lesson_name}")
  public ResponseEntity delete(@PathVariable("lesson_name") String courseName) {

    lessonService.delete(courseName);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @ApiOperation("Get all lessons")
  @ApiResponses({@io.swagger.annotations.ApiResponse(code = 200, message = "Accepted")})
  @GetMapping("/lessons")
  public ResponseEntity get(@RequestParam(value = "course", required = false) String course) {

    List<LessonModel> tags = lessonService.getAllActiveLessons(course);
    return new ResponseEntity(tags, HttpStatus.OK);
  }
}
