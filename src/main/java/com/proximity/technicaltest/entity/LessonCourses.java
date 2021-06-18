package com.proximity.technicaltest.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_lesson_courses")
public class LessonCourses {
    @EmbeddedId
    private LessonCourseKey id;

    public LessonCourses() {

    }

    public LessonCourseKey getId() {
        return id;
    }

    public void setId(final LessonCourseKey id) {
        this.id = id;
    }
}
