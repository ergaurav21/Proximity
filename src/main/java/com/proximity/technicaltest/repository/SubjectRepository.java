package com.proximity.technicaltest.repository;

import com.proximity.technicaltest.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository  extends CrudRepository<Subject, Long> {

    Subject findBySubjectName(String subject);

    @Query("select subjectName, description from Subject where active = true")
   List<Object[]> getSubjects();
}
