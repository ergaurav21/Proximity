package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Video;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class VideoRequest extends VideoUpsertRequest{
    @NotEmpty
    @Pattern(
            regexp = AliasValidator.NON_WHITESPACE_PATTERN,
            message = AliasValidator.NON_SPACE_MESSAGE)
    private String title;


    public Video toVideoEntity() {
        return Video.builder()
                .description(description)
                .title(title)
                .link(link)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }


}
