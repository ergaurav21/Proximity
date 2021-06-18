package com.proximity.technicaltest.repository;

import com.proximity.technicaltest.entity.Tags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends CrudRepository<Tags, Long> {

    @Query(value = "select tagName, description from Tags where active = true")
    List<Object[]> getTags();

    Tags findByTagName(String Tag);
}
