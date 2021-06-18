package com.proximity.technicaltest.entity;

import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_tags")
@Builder
public class Tags {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "tag_id", unique = true, updatable = false, nullable = false)
  private Long tagId;

  @Column(name = "tag_name", unique = true, nullable = false)
  private String tagName;

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

  public Tags() {}

  public Tags(
      final Long tagId,
      final String tagName,
      final String description,
      final boolean active,
      final Date creationDate,
      final Date lastModified,
      final User lastModifiedBy) {
    this.tagId = tagId;
    this.tagName = tagName;
    this.description = description;
    this.active = active;
    this.creationDate = creationDate;
    this.lastModified = lastModified;
    this.lastModifiedBy = lastModifiedBy;
  }

    public Long getTagId() {
        return tagId;
    }


    public String getTagName() {
        return tagName;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
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
}
