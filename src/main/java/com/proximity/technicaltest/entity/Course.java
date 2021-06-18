package com.proximity.technicaltest.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_courses")
@Builder
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name= "course_id", unique = true, updatable = false, nullable = false)
  private Long courseId;
  @Column(name = "course_name", unique = true, nullable = false)
  private String courseName;
  private String description;
    @Column(name = "is_active",length = 2)
  private boolean active;
  @Column(name = "creation_date", nullable = false)
  private Date creationDate;
  @Column(name = "last_modified", nullable = false)
  private Date lastModified;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "last_modified_by", referencedColumnName = "user_id")
  private User lastModifiedBy;

  @OneToMany
  @JoinTable(
      name = "tbl_course_subjects",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id"))
  private Set<Subject> subjects;

  public Course() {}

  public Course(
      final Long course_id,
      final String courseName,
      final String description,
      final boolean active,
      final Date creationDate,
      final Date lastModified,
      final User lastModifiedBy,
      final Set<Subject> subjects) {
    this.courseId = course_id;
    this.courseName = courseName;
    this.description = description;
    this.active = active;
    this.creationDate = creationDate;
    this.lastModified = lastModified;
    this.lastModifiedBy = lastModifiedBy;
    this.subjects = subjects;
  }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(final Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(final Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
