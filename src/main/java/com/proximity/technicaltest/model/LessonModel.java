package com.proximity.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class LessonModel {
  private final String title;
  private final String description;
  private final Set<CoursesModel> courses;

  @JsonCreator
  public LessonModel(
      @JsonProperty("title") final String title,
      @JsonProperty("description") final String description,
      @JsonProperty("course") final Set<CoursesModel> courses) {

    this.description = description;
    this.title = title;
    this.courses = courses;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Set<CoursesModel> getCourses() {
    return courses;
  }
}
