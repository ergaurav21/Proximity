package com.proximity.technicaltest.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_lesson_courses")
public class VideoLessons {
    @EmbeddedId
    private VideoTagsKey id;

    public VideoLessons() {

    }

    public VideoTagsKey getId() {
        return id;
    }

    public void setId(final VideoTagsKey id) {
        this.id = id;
    }
}

