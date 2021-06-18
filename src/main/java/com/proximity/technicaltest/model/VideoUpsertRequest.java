package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Video;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Data
public class VideoUpsertRequest {

    protected String description;
    protected Set<String> lessons;
    protected Set<String> tags;
    @NotEmpty
    protected String link;

    public Video toVideoEntity() {
        return Video.builder()
                .description(description)
                .link(link)
                .creationDate(new Date())
                .lastModified(new Date())
                .active(true)
                .build();
    }

}
