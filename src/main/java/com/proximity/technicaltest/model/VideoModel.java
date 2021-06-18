package com.proximity.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class VideoModel {

    private final String title;
    private final String description;
    private final Set<LessonModel> lessons;
    private final Set<TagsModel> tags;
    private final String link;

    @JsonCreator
    public VideoModel(
            @JsonProperty("title") final String title,
            @JsonProperty("description") final String description,
            @JsonProperty("link") final String link,
            @JsonProperty("lessons") final Set<LessonModel> lessons,
            @JsonProperty("tags") final Set<TagsModel> tags) {

        this.description = description;
        this.title = title;
        this.lessons = lessons;
        this.tags = tags;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<LessonModel> getLessons() {
        return lessons;
    }

    public Set<TagsModel> getTags() {
        return tags;
    }

    public String getLink() {
        return link;
    }
}
