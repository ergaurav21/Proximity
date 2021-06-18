package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Course;
import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.CourseRequest;
import com.proximity.technicaltest.model.CoursesModel;
import com.proximity.technicaltest.model.CoursesUpsertRequest;
import com.proximity.technicaltest.model.SubjectModel;
import com.proximity.technicaltest.repository.CourseRepository;
import com.proximity.technicaltest.repository.SubjectRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

  private final CourseRepository courseRepository;
  private final SubjectRepository subjectRepository;
  private final UserService userService;

  @Autowired
  public CourseService(
      CourseRepository courseRepository,
      SubjectRepository subjectRepository,
      UserService userService) {
    this.courseRepository = courseRepository;
    this.subjectRepository = subjectRepository;
    this.userService = userService;
  }

  public void create(final CourseRequest courseRequest) {
    String courseName = courseRequest.getCourseName();

    if(StringUtils.isEmpty(courseName)){
      throw new IllegalArgumentException("Empty Course title");
    }

    Set<String> subjects = courseRequest.getSubjects();
    if(subjects == null || subjects.isEmpty() ){
      throw new IllegalArgumentException("At-least 1 subject should be present in the course.");
    }
    if (getCourse(courseName) != null) {
      throw new IllegalArgumentException("Duplicate course code");
    }

    Course course = courseRequest.toCourseEntity();
    course.setSubjects(getSubjects(courseRequest.getSubjects()));
    course.setLastModified(new Date());
    course.setLastModifiedBy(userService.getLoggedInUser());
    courseRepository.save(course);
  }

  public void update(final CoursesUpsertRequest coursesUpsertRequest, final String courseName) {
    Course course = getCourse(courseName);

    if (course == null) {
      throw new NotFoundException("Course not found: "+courseName);
    }
    course.setDescription(coursesUpsertRequest.getDescription());
    course.setLastModified(new Date());
    course.setLastModifiedBy(userService.getLoggedInUser());

    Set<String> subjects = coursesUpsertRequest.getSubjects();
    if(subjects == null || subjects.isEmpty() ){
      throw new IllegalArgumentException("At-least 1 subject should be present in the course.");
    }
    course.setSubjects(getSubjects(subjects));
    courseRepository.save(course);
  }

  public void updateCourseSubjects(
      final CoursesUpsertRequest coursesUpsertRequest, final String courseName) {
    Course course = getCourse(courseName);

    if (course == null) {
      throw new NotFoundException("Course not found: "+ courseName);
    }

    Set<String> subjectSet = coursesUpsertRequest.getSubjects();
    if(subjectSet == null || subjectSet.isEmpty() ){
      throw new IllegalArgumentException("Please specify at-least 1 subject.");
    }

    Set<Subject> subjects = course.getSubjects();
    if (coursesUpsertRequest.getSubjects() != null) {
      subjects.addAll(getSubjects(subjectSet));
    }
    course.setLastModified(new Date());
    course.setLastModifiedBy(userService.getLoggedInUser());
    courseRepository.save(course);
  }

  public void delete(final String courseName) {
    Course course = getCourse(courseName);
    if (course != null) {
      courseRepository.delete(course);
    }
  }

  public List<CoursesModel> getAllActiveBySubjects(String subject) {
    List<CoursesModel> coursesModels = new ArrayList<>();
    Iterable<Course> courses = null;

    if (StringUtils.isEmpty(subject)) {
      courses = courseRepository.findAllActiveBySubject(subject);
    } else {
      courses = courseRepository.findAllActive();
    }

    Iterator<Course> courseIterator = courses.iterator();

    while (courseIterator.hasNext()) {
      Course course = courseIterator.next();
      Set<Subject> subjects = course.getSubjects();
      CoursesModel coursesModel =
          new CoursesModel(
              course.getCourseName(), course.getDescription(), getSubjectModels(subjects));
      coursesModels.add(coursesModel);
    }
    return coursesModels;
  }

  private Set<Subject> getSubjects(Set<String> subjects) {
    Set<Subject> subjectSet = new HashSet<>();

    for (String subjectName : subjects) {
      Subject subject = subjectRepository.findBySubjectName(subjectName);
      if (subject == null) {
        throw new NotFoundException("Invalid Subject: "+subjectName);
      }
      subjectSet.add(subject);
    }
    return subjectSet;
  }

  private Set<SubjectModel> getSubjectModels(Set<Subject> subjects) {
    Set<SubjectModel> subjectSet = new HashSet<>();

    for (Subject subject : subjects) {
      SubjectModel subjectModel =
          new SubjectModel(subject.getSubjectName(), subject.getDescription());
      subjectSet.add(subjectModel);
    }
    return subjectSet;
  }

  private Course getCourse(String courseName) {
    Course course = courseRepository.findAllActiveByCourse(courseName);
    return course;
  }
}
