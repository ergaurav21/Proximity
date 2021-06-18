package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Course;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CoursesUpsertRequest {

    protected String description;
    private Set<String> subjects;

    public Course toCourseEntity() {
        return Course.builder()
                .description(description)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }


}
