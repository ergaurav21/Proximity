package com.proximity.technicaltest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LessonCourseKey implements Serializable {

    @Column(name = "lesson_id")
    private  Long lessonId;
    @Column(name = "course_id")
    private Long courseId;

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(final Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(final Long courseId) {
        this.courseId = courseId;
    }
}
