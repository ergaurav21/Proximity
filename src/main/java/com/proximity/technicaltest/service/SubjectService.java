package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.entity.User;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.SubjectModel;
import com.proximity.technicaltest.model.SubjectUpsertRequest;
import com.proximity.technicaltest.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubjectService {

  private final SubjectRepository subjectRepository;
  private final UserService userService;

  @Autowired
  public SubjectService(SubjectRepository subjectRepository, UserService userService) {
    this.subjectRepository = subjectRepository;
    this.userService = userService;
  }

  public void create(Subject subject) {
    if (subjectRepository.findBySubjectName(subject.getSubjectName()) != null) {
      throw new IllegalArgumentException("Duplicate Subject: " + subject.getSubjectName());
    }
    subject.setLastModifiedBy(userService.getLoggedInUser());
    subjectRepository.save(subject);
  }

  public void update(SubjectUpsertRequest subjectRequest, String subjectName) {
    updateOrDelete(subjectName,subjectRequest.getDescription(),true);
  }

  public void delete(String subjectName) {
    updateOrDelete(subjectName,null,false);
  }

  public List<SubjectModel> getAllSubjects() {
    List<SubjectModel> subjectModels = new ArrayList<>();
    List<Object[]> subjects = subjectRepository.getSubjects();

    for (Object[] objects : subjects) {
      SubjectModel subjectModel = new SubjectModel((String) objects[0], (String) objects[1]);
      subjectModels.add(subjectModel);
    }
    return subjectModels;
  }

  private void updateOrDelete(String subjectName, String description, boolean active) {
    Subject subject = subjectRepository.findBySubjectName(subjectName);
    if (subject == null) {
      throw new NotFoundException("Subject not found: "+subjectName);
    }

    User user = userService.getLoggedInUser();
    subject.setLastModified(new Date());
    if (active) {
      // Only in case of update description needs to be updated
      subject.setDescription(description);
    }
    subject.setActive(active);
    subject.setLastModifiedBy(user);
    subjectRepository.save(subject);
  }
}
