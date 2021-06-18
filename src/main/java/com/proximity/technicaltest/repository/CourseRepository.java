package com.proximity.technicaltest.repository;

import com.proximity.technicaltest.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query(value = "from Course c join CourseSubjects cb on cb.id.courseId = c.courseId  join Subject  s on s.subjectId = cb.id.subjectId where s.subjectName like %:subject% and c.active = true and s.active = true")
    Iterable<Course> findAllActiveBySubject(@Param("subject") String subject);

    @Query(value = "from Course where active = true")
    Iterable<Course> findAllActive();

    @Query(value = "from Course where active = true and courseName=?1")
    Course findAllActiveByCourse(String CourseName);


}
