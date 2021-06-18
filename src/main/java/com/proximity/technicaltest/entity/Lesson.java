package com.proximity.technicaltest.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_lesson")
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "lesson_id", unique = true, updatable = false, nullable = false)
    private Long lessonId;
    private String title;
    private String description;
    @Column(name = "is_active",length = 2)
    private boolean active;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "last_modified")
    private Date lastModified;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "last_modified_by", referencedColumnName = "user_id")
    private User lastModifiedBy;
    @OneToMany
    @JoinTable(name = "tbl_lesson_courses",joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    public Lesson(final Long lessonId, final String title, final String description, final boolean active, final Date creationDate, final Date lastModified, final User lastModifiedBy, final Set<Course> courses) {
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.active = active;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.courses = courses;
    }

    public Lesson() {

    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(final Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(final Set<Course> courses) {
        this.courses = courses;
    }
}
