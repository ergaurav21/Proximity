package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Course;
import com.proximity.technicaltest.entity.Lesson;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.CoursesModel;
import com.proximity.technicaltest.model.LessonModel;
import com.proximity.technicaltest.model.LessonRequest;
import com.proximity.technicaltest.model.LessonUpsertRequest;
import com.proximity.technicaltest.repository.CourseRepository;
import com.proximity.technicaltest.repository.LessonRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LessonService {
  private final LessonRepository lessonRepository;
  private final CourseRepository courseRepository;
  private final UserService userService;

  @Autowired
  public LessonService(
      LessonRepository lessonRepository,
      CourseRepository courseRepository,
      UserService userService) {
    this.lessonRepository = lessonRepository;
    this.courseRepository = courseRepository;
    this.userService = userService;
  }

  public void create(LessonRequest lessonRequest) {
    Set<String> courses = lessonRequest.getCourses();
    if(courses == null || courses.isEmpty()){
      throw new IllegalArgumentException("A lesson should be mapped to at-least 1 course.");
    }
    if (getLesson(lessonRequest.getTitle()) != null) {
      throw new IllegalArgumentException("Duplicate course code");
    }

    Lesson lesson = lessonRequest.toLessonEntity();
    lesson.setCourses(getCourses(courses));
    lesson.setLastModified(new Date());
    lesson.setLastModifiedBy(userService.getLoggedInUser());
    lessonRepository.save(lesson);
  }

  public void update(LessonUpsertRequest lessonsUpsertRequest, String courseName) {
    Lesson lesson = getLesson(courseName);
    if (lesson == null) {
      throw new NotFoundException("Lesson not found:", courseName);
    }

    Set<String> courses = lessonsUpsertRequest.getCourses();
    if(courses == null || courses.isEmpty()){
      throw new IllegalArgumentException("A lesson should be mapped to at-least 1 course.");
    }
    lesson.setDescription(lessonsUpsertRequest.getDescription());
    lesson.setLastModified(new Date());
    lesson.setLastModifiedBy(userService.getLoggedInUser());
    lesson.setCourses(getCourses(courses));
    lessonRepository.save(lesson);
  }

  public void delete(final String courseName) {
    Lesson lesson = getLesson(courseName);
    if (lesson != null) {
      lessonRepository.delete(lesson);
    }
  }

  public List<LessonModel> getAllActiveLessons(final String subject) {
    List<LessonModel> lessonModels = new ArrayList<>();
    Iterable<Lesson> lessons = null;

    if (StringUtils.isEmpty(subject)) {
      lessons = lessonRepository.findAllActiveByCourse(subject);
    } else {
      lessons = lessonRepository.findAllActive();
    }

    Iterator<Lesson> courseIterator = lessons.iterator();

    while (courseIterator.hasNext()) {
      Lesson lesson = courseIterator.next();
      Set<Course> courseSet = lesson.getCourses();
      LessonModel lessonModel =
          new LessonModel(lesson.getTitle(), lesson.getDescription(), getCourseModels(courseSet));
      lessonModels.add(lessonModel);
    }
    return lessonModels;
  }

  public void updateCourses(
      final LessonUpsertRequest lessonUpsertRequest, final String title) {
    Lesson lesson = getLesson(title);
    if (lesson == null) {
      throw new NotFoundException("Lesson not found:", title);
    }

    Set<String> courses = lessonUpsertRequest.getCourses();
    if(courses == null || courses.isEmpty()){
      throw new IllegalArgumentException("Please specify at-least 1 course.");
    }

    Set<Course> coursesSet = lesson.getCourses();
    if (courses != null) {
      coursesSet.addAll(getCourses(courses));
    }
    lesson.setLastModified(new Date());
    lesson.setLastModifiedBy(userService.getLoggedInUser());
    lessonRepository.save(lesson);
  }

  private Set<CoursesModel> getCourseModels(Set<Course> courses) {
    Set<CoursesModel> coursesModelSet = new HashSet<>();

    for (Course course : courses) {
      CoursesModel coursesModel =
          new CoursesModel(course.getCourseName(), course.getDescription(), Set.of());
      coursesModelSet.add(coursesModel);
    }
    return coursesModelSet;
  }

  private Set<Course> getCourses(Set<String> courses) {
    Set<Course> courseSet = new HashSet<>();

    for (String courseName : courses) {
      Course course = courseRepository.findAllActiveByCourse(courseName);
      if (course == null) {
        throw new NotFoundException("Invalid Course: "+ courseName);
      }
      courseSet.add(course);
    }
    return courseSet;
  }

  private Lesson getLesson(String title) {
    Lesson lesson = lessonRepository.findAllActiveByTitle(title);
    return lesson;
  }
}
