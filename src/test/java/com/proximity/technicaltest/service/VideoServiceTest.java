package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Lesson;
import com.proximity.technicaltest.entity.Video;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.VideoRequest;
import com.proximity.technicaltest.repository.LessonRepository;
import com.proximity.technicaltest.repository.TagsRepository;
import com.proximity.technicaltest.repository.VideoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {

  @Mock private VideoRepository videoRepository;
  @Mock private TagsRepository tagsRepository;
  @Mock private LessonRepository lessonRepository;
  @Mock private UserService userService;
  private VideoService videoService;

  @Before
  public void setup() {
    videoService = new VideoService(videoRepository, tagsRepository, lessonRepository, userService);
  }

  @Test
  public void givenDuplicateVideo_whenCreate_thenThrowIllegalArgumentException() {
      //Given
      VideoRequest videoRequest = new VideoRequest();
      videoRequest.setTitle("test");
      Video video = new Video();

      when(videoRepository.findAllActiveByTitle(any())).thenReturn(video);

      // When
      assertThatThrownBy(() -> videoService.create(videoRequest))
      // Then
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Duplicate video code");
  }

    @Test
    public void givenMissingLessons_whenCreate_thenThrowIllegalArgumentException() {
        //Given
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setTitle("test");

        // When
        assertThatThrownBy(() -> videoService.create(videoRequest))
        // Then
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Video should be mapped to at-least 1 subject");
    }

    @Test
    public void givenInvalidLessons_whenCreate_thenThrowIllegalArgumentException() {
        //Given
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setTitle("test");
        videoRequest.setLessons(Set.of("hindi"));

        when(videoRepository.findAllActiveByTitle(any())).thenReturn(null);
        when(lessonRepository.findAllActiveByTitle("test")).thenReturn(null);

        // When
        assertThatThrownBy(() -> videoService.create(videoRequest))
        // Then
       .isInstanceOf(NotFoundException.class)
       .hasMessage("Invalid Lesson: hindi");
    }

    @Test
    public void givenInvalidTags_whenCreate_thenThrowIllegalArgumentException() {
        //Given
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setTitle("test");
        videoRequest.setLessons(Set.of("hindi"));
        videoRequest.setTags(Set.of("tags"));
        Lesson lesson = new Lesson();

        when(lessonRepository.findAllActiveByTitle(any())).thenReturn(lesson);

        // When
        assertThatThrownBy(() -> videoService.create(videoRequest))
        // Then
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Invalid Tag: tags");
    }

    @Test
    public void givenValidRequest_whenCreate_thenSuccess() {
        //Given
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setTitle("test");
        videoRequest.setLessons(Set.of("hindi"));
        Lesson lesson = new Lesson();

        when(lessonRepository.findAllActiveByTitle(any())).thenReturn(lesson);

        // Then
        videoService.create(videoRequest);

    }
}
