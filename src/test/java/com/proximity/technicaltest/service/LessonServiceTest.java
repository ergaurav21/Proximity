package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Course;
import com.proximity.technicaltest.entity.Lesson;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.LessonRequest;
import com.proximity.technicaltest.repository.CourseRepository;
import com.proximity.technicaltest.repository.LessonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LessonServiceTest {

  @Mock private LessonRepository lessonRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private UserService userService;
  @Mock private Lesson lesson;
  @Mock private Course course;

  private LessonService lessonService;

  @Before
  public void setup() {
    lessonService = new LessonService(lessonRepository, courseRepository, userService);
  }

  @Test
  public void givenMissingCourse_whenCreate_throwIllegalArgumentException() {
    // Given
    LessonRequest lessonRequest = new LessonRequest();

    // When
    assertThatThrownBy(() -> lessonService.create(lessonRequest))
    // Then
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessage("A lesson should be mapped to at-least 1 course.");
  }

  @Test
  public void givenDuplicateCourse_whenCreate_throwIllegalArgumentException() {
    // Given
    LessonRequest lessonRequest = new LessonRequest();
    lessonRequest.setCourses(Set.of("lesson"));

    when(lessonRepository.findAllActiveByTitle(any())).thenReturn(lesson);

    // When
    assertThatThrownBy(() -> lessonService.create(lessonRequest))
   // Then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Duplicate course code");
  }

  @Test
  public void givenInvalidCourse_whenUpdateCourseSubjects_throwNotFoundException() {
    // Given
    String courseName = "GCP";
    LessonRequest lessonRequest = new LessonRequest();
    lessonRequest.setCourses(Set.of(courseName));

    when(courseRepository.findAllActiveByCourse(any())).thenReturn(null);

    // When
    assertThatThrownBy(() -> lessonService.create(lessonRequest))
   // Then
    .isInstanceOf(NotFoundException.class)
    .hasMessage("Invalid Course: " + courseName);
  }

  @Test
  public void givenValidRequest_whenUpdateCourseSubjects_returnSuccess() {
    // Given
    String courseName = "GCP";
    LessonRequest lessonRequest = new LessonRequest();
    lessonRequest.setCourses(Set.of(courseName));

    when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);

    // Then
    lessonService.create(lessonRequest);
  }

}
