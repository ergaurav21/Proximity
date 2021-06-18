package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Lesson;
import com.proximity.technicaltest.entity.Tags;
import com.proximity.technicaltest.entity.Video;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.*;
import com.proximity.technicaltest.repository.LessonRepository;
import com.proximity.technicaltest.repository.TagsRepository;
import com.proximity.technicaltest.repository.VideoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VideoService {

  private final VideoRepository videoRepository;
  private final TagsRepository tagsRepository;
  private final LessonRepository lessonRepository;
  private final UserService userService;

  @Autowired
  public VideoService(
      VideoRepository videoRepository,
      TagsRepository tagsRepository,
      LessonRepository lessonRepository,
      UserService userService) {
    this.videoRepository = videoRepository;
    this.tagsRepository = tagsRepository;
    this.lessonRepository = lessonRepository;
    this.userService = userService;
  }

  public List<VideoModel> getAllVideos(final String title, final String tag) {
    Iterable<Video> videos = null;
    List<VideoModel> videoModels = new ArrayList<>();

    if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(tag)) {
      videos = videoRepository.findAllActiveByTagsAndTitle(tag, title);
    } else if (StringUtils.isNotEmpty(title)) {
      videos = videoRepository.filterVideosByTitle(title);
    } else if (StringUtils.isNotEmpty(tag)) {
      videos = videoRepository.findAllActiveByTags(tag);
    } else {
      videos = videoRepository.findAllActive();
    }

    Iterator<Video> videoIterator = videos.iterator();

    while (videoIterator.hasNext()) {
      Video video = videoIterator.next();
      VideoModel videoModel =
          new VideoModel(
              video.getTitle(),
              video.getDescription(),
              video.getLink(),
              getLessonModels(video.getLessons()),
              getTagModels(video.getTags()));
      videoModels.add(videoModel);
    }
    return videoModels;
  }

  public void delete(final String title) {
    Video video = getVideo(title);
    if (video != null) {
      videoRepository.delete(video);
    }
  }

  public void update(final VideoUpsertRequest videoUpsertRequest, final String title) {
    Video video = getVideo(title);
    if (video == null) {
      throw new NotFoundException("Video not found: "+title);
    }

    Set<String> lessons = videoUpsertRequest.getLessons();
    if (lessons == null || lessons.isEmpty()) {
      throw new IllegalArgumentException("At-least 1 subject should be present in the course.");
    }

    Set<String> tags = videoUpsertRequest.getTags();

    video.setDescription(videoUpsertRequest.getDescription());
    video.setLastModified(new Date());
    video.setLastModifiedBy(userService.getLoggedInUser());
    if (tags != null) {
      video.setTags(getTags(tags));
    }
    video.setLessons(getLessons(lessons));
    videoRepository.save(video);
  }

  public void create(final VideoRequest videoRequest) {
    if (getVideo(videoRequest.getTitle()) != null) {
      throw new IllegalArgumentException("Duplicate video code");
    }
    Video video = videoRequest.toVideoEntity();

    Set<String> lessons = videoRequest.getLessons();
    if (lessons == null || lessons.isEmpty()) {
      throw new IllegalArgumentException("Video should be mapped to at-least 1 subject");
    }

    Set<String> tags = videoRequest.getTags();
    video.setLessons(getLessons(lessons));
    if (tags != null) {
      video.setTags(getTags(tags));
    }
    video.setLastModified(new Date());
    video.setLastModifiedBy(userService.getLoggedInUser());
    videoRepository.save(video);
  }

  private Set<Lesson> getLessons(Set<String> lessons) {
    Set<Lesson> lessonSet = new HashSet<>();

    for (String title : lessons) {
      Lesson lesson = lessonRepository.findAllActiveByTitle(title);
      if (lesson == null) {
        throw new NotFoundException("Invalid Lesson: "+title);
      }
      lessonSet.add(lesson);
    }
    return lessonSet;
  }

  private Set<Tags> getTags(Set<String> tags) {
    Set<Tags> tagsSet = new HashSet<>();

    for (String title : tags) {
      Tags tag = tagsRepository.findByTagName(title);
      if (tag == null) {
        throw new NotFoundException("Invalid Tag: "+ title);
      }
      tagsSet.add(tag);
    }
    return tagsSet;
  }

  public Video getVideo(String title) {
    Video video = videoRepository.findAllActiveByTitle(title);
    return video;
  }

  public void updateLessonsAndTags(
      final VideoUpsertRequest videoUpsertRequest, final String title) {
    Video video = getVideo(title);
    if (video == null) {
      throw new NotFoundException("Video not found:", title);
    }
    video.setLastModified(new Date());
    video.setLastModifiedBy(userService.getLoggedInUser());

    Set<String> tagsSet = videoUpsertRequest.getTags();

    if (tagsSet != null && !tagsSet.isEmpty()) {
      video.getTags().addAll(getTags(tagsSet));
    }

    Set<String> lessons = videoUpsertRequest.getLessons();
    if (lessons == null) {
      throw new IllegalArgumentException("Please specify at-least 1 lesson.");
    }

    video.getLessons().addAll(getLessons(lessons));

    videoRepository.save(video);
  }

  private Set<LessonModel> getLessonModels(Set<Lesson> lessons) {
    Set<LessonModel> lessonModels = new HashSet<>();

    for (Lesson lesson : lessons) {
      LessonModel lessonModel =
          new LessonModel(lesson.getTitle(), lesson.getDescription(), Set.of());
      lessonModels.add(lessonModel);
    }
    return lessonModels;
  }

  private Set<TagsModel> getTagModels(Set<Tags> tags) {
    Set<TagsModel> coursesModelSet = new HashSet<>();

    for (Tags tag : tags) {
      TagsModel tagsModel = new TagsModel(tag.getTagName(), tag.getDescription());
      coursesModelSet.add(tagsModel);
    }
    return coursesModelSet;
  }

  public VideoModel getVideoDetails(String title) {
    Video video = videoRepository.findAllActiveByTitle(title);
    if (video == null) {
      throw new IllegalArgumentException("No Video found");
    }
    VideoModel videoModel =
        new VideoModel(title, video.getDescription(), video.getLink(), Set.of(), Set.of());
    return videoModel;
  }
}
