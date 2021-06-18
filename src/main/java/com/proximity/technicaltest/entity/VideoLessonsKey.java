package com.proximity.technicaltest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VideoLessonsKey implements Serializable {

    @Column(name = "lesson_id")
    private  Long lessonId;
    @Column(name = "video_id")
    private Long videoId;

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(final Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(final Long videoId) {
        this.videoId = videoId;
    }
}
