package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Course;
import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.CourseRequest;
import com.proximity.technicaltest.model.CoursesUpsertRequest;
import com.proximity.technicaltest.repository.CourseRepository;
import com.proximity.technicaltest.repository.SubjectRepository;
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
public class CourseServiceTest {

    @Mock
    private  CourseRepository courseRepository;
    @Mock
    private  SubjectRepository subjectRepository;
    @Mock
    private  UserService userService;
    @Mock
    private Course course;
    @Mock
    private Subject subject;
    private CourseService courseService;
    private final String COURSE_NAME = "gcp";

  @Before
  public void setUp() {
    courseService = new CourseService(courseRepository, subjectRepository, userService);
  }

  @Test
  public void givenMissingCourse_whenCreate_throwIllegalArgumentException() {
    // Given
    CourseRequest courseRequest = new CourseRequest();

    // When
    assertThatThrownBy(() -> courseService.create(courseRequest))
   //Then
   .isInstanceOf(IllegalArgumentException.class)
   .hasMessage("Empty Course title");
  }

  @Test
  public void givenMissingSubject_whenCreate_throwIllegalArgumentException() {
    // Given
    CourseRequest courseRequest = new CourseRequest();
    courseRequest.setCourseName(COURSE_NAME);
    when(subjectRepository.findBySubjectName(any())).thenReturn(null);

    // When
    assertThatThrownBy(() -> courseService.create(courseRequest))
    // Then
   .isInstanceOf(IllegalArgumentException.class)
   .hasMessage("At-least 1 subject should be present in the course.");
  }

    @Test
    public void givenDuplicateCourse_whenCreate_throwIllegalArgumentException() {
        // Given
        String subject = "hindi";
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseName(COURSE_NAME);
        courseRequest.setSubjects(Set.of(subject));

        when(subjectRepository.findBySubjectName(any())).thenReturn(null);
        when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);

        // When
        assertThatThrownBy(() -> courseService.create(courseRequest))
        // Then
       .isInstanceOf(IllegalArgumentException.class)
       .hasMessage("Duplicate course code");
    }

    @Test
    public void givenInvalidSubject_whenCreate_throwIllegalArgumentException() {
        // Given
        String subject = "hindi";
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseName(COURSE_NAME);
        courseRequest.setSubjects(Set.of(subject));

        when(subjectRepository.findBySubjectName(any())).thenReturn(null);


        // When
        assertThatThrownBy(() -> courseService.create(courseRequest))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid Subject: "+subject);
    }

    @Test
    public void givenInvalidCourse_whenUpdate_throwNotFoundException() {
        // Given
        String courseName = "GCP";
        CoursesUpsertRequest courseRequest = new CourseRequest();

        // When
        assertThatThrownBy(() -> courseService.update(courseRequest, courseName))
                // Then
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Course not found: "+courseName);
    }

    @Test
    public void givenMissingSubject_whenUpdate_throwNotFoundException() {
        // Given
        String courseName = "GCP";
        CoursesUpsertRequest courseRequest = new CourseRequest();

        when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);

        // When
        assertThatThrownBy(() -> courseService.update(courseRequest, courseName))
                // Then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("At-least 1 subject should be present in the course.");
    }

    @Test
    public void givenInvalidSubject_whenUpdate_throwNotFoundException() {
        // Given
        String courseName = "GCP";
        String subjectName = "hindi";
        CoursesUpsertRequest courseRequest = new CourseRequest();
        courseRequest.setSubjects(Set.of(subjectName));

        when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);
        when(subjectRepository.findBySubjectName(any())).thenReturn(null);

        // When
        assertThatThrownBy(() -> courseService.update(courseRequest, courseName))
        // Then
        .isInstanceOf(NotFoundException.class)
       .hasMessage("Invalid Subject: "+subjectName);
    }

    @Test
    public void givenValidCourse_whenDelete_returnSuccess() {
        // Given
        String courseName = "GCP";

        //When
        when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);

        // Then
        courseService.delete(courseName);
    }

    @Test
    public void givenInvalidCourse_whenUpdateCourseSubjects_throwNotFoundException() {
        // Given
        String courseName = "GCP";
        CoursesUpsertRequest courseRequest = new CourseRequest();

        // When
        assertThatThrownBy(() -> courseService.updateCourseSubjects(courseRequest, courseName))
        // Then
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Course not found: "+courseName);
    }

    @Test
    public void givenMissingSubject_whenUpdateCourseSubjects_throwNotFoundException() {
        // Given
        String courseName = "GCP";
        CoursesUpsertRequest courseRequest = new CourseRequest();

        when(courseRepository.findAllActiveByCourse(any())).thenReturn(course);

        // When
        assertThatThrownBy(() -> courseService.update(courseRequest, courseName))
        // Then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At-least 1 subject should be present in the course.");
    }


}
