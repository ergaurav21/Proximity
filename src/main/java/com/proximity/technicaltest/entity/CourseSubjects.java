package com.proximity.technicaltest.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_course_subjects")
public class CourseSubjects {
    @EmbeddedId
    private CourseSubjectKey id;

    public CourseSubjects() {

    }

    public CourseSubjectKey getId() {
        return id;
    }

    public void setId(final CourseSubjectKey id) {
        this.id = id;
    }
}
