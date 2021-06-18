package com.proximity.technicaltest.entity;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user" )
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "user_id", unique = true, updatable = false, nullable = false)
    private Long userId;
    @Column(name= "user_name", unique = true, updatable = false, nullable = false)
    private String userName;
    private String password;
    @Column(name = "is_active",length = 2)
    private boolean active ;
    @Column(name = "is_instructor")
    private boolean instructor;
    @Column(name= "first_name", nullable = false)
    private String firstName;
    @Column(name= "last_name", nullable = false)
    private String lastName;
    private String email;

  public User(
      final String userName,
      final String password,
      final boolean active,
      final boolean instructor,
      final String firstName,
      final String lastName,
      final String email) {
    this.userName = userName;
    this.password = password;
    this.active = active;
    this.instructor = instructor;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(
      final Long userId,
      final String userName,
      final String password,
      final boolean active,
      final boolean instructor,
      final String firstName,
      final String lastName,
      final String email) {
    this(userName, password, active, instructor, firstName, lastName, email);
  }

    public User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public void setInstructor(final boolean instructor) {
        this.instructor = instructor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
