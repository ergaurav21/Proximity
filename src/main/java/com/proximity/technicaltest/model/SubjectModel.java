package com.proximity.technicaltest.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectModel {

    private final String subjectName;
    private final String description;


    @JsonCreator
    public SubjectModel(
            @JsonProperty("subject_name") final String subjectName,
            @JsonProperty("description") final String description) {

      this.description = description;
      this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }
}
