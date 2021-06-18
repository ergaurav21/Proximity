package com.proximity.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CoursesModel {

  private final String courseName;
  private final String description;
  private final Set<SubjectModel> subjects;

  @JsonCreator
  public CoursesModel(
      @JsonProperty("course_name") final String courseName,
      @JsonProperty("description") final String description,
      @JsonProperty("subjects") final Set<SubjectModel> subjects) {

    this.description = description;
    this.courseName = courseName;
    this.subjects = subjects;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getDescription() {
    return description;
  }

  public Set<SubjectModel> getSubjects() {
    return subjects;
  }
}
