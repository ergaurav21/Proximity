package com.proximity.technicaltest.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tbl_video")
@Builder
public class Video {
    @Id
    private Long video_id;
    private String title;
    private String description;
    private String link;
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
    @JoinTable(name = "tbl_video_lessons",joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private Set<Lesson> lessons;
    @OneToMany
    @JoinTable(name = "tbl_video_tags",joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tags> tags;

    public Video() {

    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(final Long video_id) {
        this.video_id = video_id;
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

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
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

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(final Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public void setTags(final Set<Tags> tags) {
        this.tags = tags;
    }

    public Video(final Long video_id, final String title, final String description, final String link, final boolean active, final Date creationDate, final Date lastModified, final User lastModifiedBy, final Set<Lesson> lessons, final Set<Tags> tags) {
        this.video_id = video_id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.active = active;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.lessons = lessons;
        this.tags = tags;
    }
}
