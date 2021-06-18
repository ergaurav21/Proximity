package com.proximity.technicaltest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CourseSubjectKey implements Serializable {

    @Column(name = "subject_id")
    private  Long subjectId;
    @Column(name = "course_id")
    private Long courseId;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(final Long courseId) {
        this.courseId = courseId;
    }
}
