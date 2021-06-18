package com.proximity.technicaltest.model;

import com.proximity.technicaltest.entity.User;
import com.proximity.technicaltest.validator.AliasValidator;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegistrationRequest {

  @NotEmpty
  @Pattern(regexp = AliasValidator.NON_WHITESPACE_PATTERN, message = AliasValidator.NON_SPACE_MESSAGE)
  private String firstname;
  @NotEmpty
  @Pattern(regexp = AliasValidator.NON_WHITESPACE_PATTERN, message = AliasValidator.NON_SPACE_MESSAGE)
  private String lastname;
  private boolean instructor;
  @Pattern(regexp = AliasValidator.NON_WHITESPACE_PATTERN, message = AliasValidator.NON_SPACE_MESSAGE)
  @NotEmpty
  private String username;
  private String email;
  @NotEmpty
  @Pattern(regexp = AliasValidator.NON_WHITESPACE_PATTERN, message = AliasValidator.NON_SPACE_MESSAGE)
  private String password;

  public User toUserEntity() {
    String encodedPassword = new BCryptPasswordEncoder().encode(password);
    return User.builder()
        .firstName(this.firstname)
        .lastName(this.lastname)
        .password(encodedPassword)
        .instructor(this.instructor)
        .email(this.email)
        .active(true)
        .userName(this.username)
        .build();
  }
}
