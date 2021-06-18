package com.proximity.technicaltest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class VideoTagsKey implements Serializable {

    @Column(name = "video_id")
    private  Long videoId;
    @Column(name = "tag_id")
    private Long tagId;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(final Long videoId) {
        this.videoId = videoId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(final Long tagId) {
        this.tagId = tagId;
    }
}
