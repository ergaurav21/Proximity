package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class SubjectUpsertRequest {

  @NotEmpty
  protected String description;


  public Subject toSubjectEntity() {

    return Subject.builder()
        .description(description)
        .creationDate(new Date())
        .lastModified(new Date())
        .active(true)
        .build();
  }
}
