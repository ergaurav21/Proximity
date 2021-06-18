package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Tags;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class TagsUpsertRequest {

  @NotEmpty
  protected String description;


  public Tags toTagsEntity() {
    return Tags.builder()
        .description(description)
        .creationDate(new Date())
        .lastModified(new Date())
        .active(true)
        .build();
  }
}
