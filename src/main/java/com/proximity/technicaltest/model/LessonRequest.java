package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Lesson;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class LessonRequest extends LessonUpsertRequest{

    @NotEmpty
    @Pattern(
            regexp = AliasValidator.NON_WHITESPACE_PATTERN,
            message = AliasValidator.NON_SPACE_MESSAGE)
    private String title;



    public Lesson toLessonEntity() {

        return Lesson.builder()
                .title(title)
                .description(description)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }
}
