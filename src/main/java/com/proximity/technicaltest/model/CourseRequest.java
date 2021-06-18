package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Course;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class CourseRequest extends CoursesUpsertRequest{
    @NotEmpty
    @Pattern(
            regexp = AliasValidator.NON_WHITESPACE_PATTERN,
            message = AliasValidator.NON_SPACE_MESSAGE)
    private String courseName;



    public Course toCourseEntity() {

        return Course.builder()
                .courseName(courseName)
                .description(description)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }
}
