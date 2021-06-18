package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Lesson;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class LessonUpsertRequest {

    protected String description;
    private Set<String> courses;

    public Lesson toLessonEntity() {
        return Lesson.builder()
                .description(description)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }

}
