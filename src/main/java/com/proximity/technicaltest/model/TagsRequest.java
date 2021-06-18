package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Tags;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class TagsRequest extends TagsUpsertRequest {

  @NotEmpty
  @Pattern(
      regexp = AliasValidator.NON_WHITESPACE_PATTERN,
      message = AliasValidator.NON_SPACE_MESSAGE)
  private String tagName;

  @Override
  public Tags toTagsEntity() {
    return Tags.builder()
        .tagName(tagName)
        .description(description)
        .creationDate(new Date())
        .lastModified(new Date())
        .active(true)
        .build();
  }
}
