package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.entity.User;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class SubjectRequest extends  SubjectUpsertRequest{

  @NotEmpty
  @Pattern(regexp = AliasValidator.NON_WHITESPACE_PATTERN, message = AliasValidator.NON_SPACE_MESSAGE)
  private String subjectName;


  public Subject toSubjectEntity() {

    return Subject.builder()
        .subjectName(subjectName)
        .description(description)
        .creationDate(new Date())
        .lastModified(new Date())
        .active(true)
        .build();
  }
}
