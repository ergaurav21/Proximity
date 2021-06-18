package com.proximity.technicaltest.service;

import com.proximity.technicaltest.common.Utility;
import com.proximity.technicaltest.entity.Tags;
import com.proximity.technicaltest.entity.User;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.TagsModel;
import com.proximity.technicaltest.model.TagsUpsertRequest;
import com.proximity.technicaltest.repository.TagsRepository;
import com.proximity.technicaltest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TagsService {

  private final TagsRepository tagsRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  @Autowired
  public TagsService(TagsRepository tagsRepository, UserRepository userRepository, UserService userService) {
    this.tagsRepository = tagsRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  public void create(final Tags tags) {
    if (tagsRepository.findByTagName(tags.getTagName()) != null) {
      throw new IllegalArgumentException(tags.getTagName() + " Already exist.");
    }
    tags.setLastModifiedBy(userService.getLoggedInUser());
    tagsRepository.save(tags);
  }

  public void update(final TagsUpsertRequest tagsUpsertRequest, final String tagName) {
    updateOrDelete(tagName, tagsUpsertRequest.getDescription(), true);
  }

  public void delete(final String tagName) {
    updateOrDelete(tagName, "", false);
  }

  public List<TagsModel> getAllTags() {
    List<TagsModel> tagsModels = new ArrayList<>();
    List<Object[]> tags = tagsRepository.getTags();

    for (Object[] objects : tags) {
      TagsModel tagsModel = new TagsModel((String) objects[0], (String) objects[1]);
      tagsModels.add(tagsModel);
    }
    return tagsModels;
  }

  private void updateOrDelete(String tagName, String description, boolean active) {
    Tags tags = tagsRepository.findByTagName(tagName);
    if (tags == null) {
      throw new NotFoundException(tagName, "Tag not found");
    }
    tags.setLastModified(new Date());
    if (active) {
      // Only in case of update description needs to be updated
      tags.setDescription(description);
    }
    tags.setActive(active);
    tags.setLastModifiedBy(userService.getLoggedInUser());
    tagsRepository.save(tags);
  }
}
