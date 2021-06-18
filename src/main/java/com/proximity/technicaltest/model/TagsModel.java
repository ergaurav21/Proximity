package com.proximity.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsModel {

    private final String tagName;
    private final String description;


    @JsonCreator
    public TagsModel(
            @JsonProperty("tag_name") final String tagName,
            @JsonProperty("description") final String description) {

        this.description = description;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }
}
