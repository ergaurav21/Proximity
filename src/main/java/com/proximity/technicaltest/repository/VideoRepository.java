package com.proximity.technicaltest.repository;

import com.proximity.technicaltest.entity.Video;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {

    @Query(value = "from Video c join VideoTags cb on cb.id.videoId = c.video_id  join Tags  s on s.tagId = cb.id.tagId where s.tagName like %:tags% and c.active = true and s.active = true")
    Iterable<Video> findAllActiveByTags(@Param("tags") String tags);

    @Query(value = "from Course where active = true")
    Iterable<Video> findAllActive();

    @Query(value = "from Video where active = true and title = ?1 and active = true")
    Video findAllActiveByTitle(String title);

    @Query(value = "from Video where active = true and title like %:title%  and active = true")
     Iterable<Video> filterVideosByTitle(@Param("title") String title);

    @Query(value = "from Video c join VideoTags cb on cb.id.videoId = c.video_id  join Tags  s on s.tagId = cb.id.tagId where s.tagName like %:tags% and c.title like %:title% and c.active = true and s.active = true")
    Iterable<Video> findAllActiveByTagsAndTitle(@Param("tags") String tags, @Param("title") String title);
}
