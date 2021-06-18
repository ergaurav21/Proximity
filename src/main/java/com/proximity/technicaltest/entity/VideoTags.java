package com.proximity.technicaltest.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_video_tags")
public class VideoTags {
    @EmbeddedId
    private VideoTagsKey id;

    public VideoTags() {

    }

    public VideoTagsKey getId() {
        return id;
    }

    public void setId(final VideoTagsKey id) {
        this.id = id;
    }
}
