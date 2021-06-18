package com.proximity.technicaltest.repository;

import com.proximity.technicaltest.entity.Lesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends CrudRepository<Lesson,Long>{


    @Query(value = "from Lesson c join LessonCourses cb on cb.id.lessonId = c.lessonId  join Course  s on s.courseId = cb.id.courseId where s.courseName like %:course% and c.active = true and s.active = true")
    Iterable<Lesson> findAllActiveByCourse(@Param("course") String course);

    @Query(value = "from Lesson where active = true")
    Iterable<Lesson> findAllActive();

    @Query(value = "from Lesson where active = true and title=?1")
    Lesson findAllActiveByTitle(String title);
}
