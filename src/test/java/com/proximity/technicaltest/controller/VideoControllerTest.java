package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.VideoModel;
import com.proximity.technicaltest.service.VideoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.proximity.technicaltest.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VideoControllerTest {
    @Mock
    private VideoService videoService;
    private MockMvc mockMvc;

  @Before
  public void setUp() {
    VideoController videoController = new VideoController(videoService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(videoController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

    @Test
    public void givenInvalidRequest_whenCreate_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletPostRequestBuilder("/videos"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

    @Test
    public void givenValidRequest_whenCreate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPostRequestBuilder("/videos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "    \"title\" : \"GCP\",\n"
                            + "    \"description\": \"Google cloud certification course.\",\n"
                            + "    \"lessons\": [\"English\"],\n"
                            + "    \"tags\": [\"mytag\"],\n"
                            + "    \"link\": \"youtube.com\"\n"
                            + "   \n"
                            + "}"))
            .andExpect(MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void givenInvalidRequest_whenUpdate_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletPutRequestBuilder("/videos"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

    @Test
    public void givenValidRequest_whenUpdate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPutRequestBuilder("/videos/t")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "    \"title\" : \"GCP\",\n"
                            + "    \"description\": \"Google cloud certification course.\",\n"
                            + "    \"lessons\": [\"English\"],\n"
                            + "    \"tags\": [\"mytag\"],\n"
                            + "    \"link\": \"youtube.com\"\n"
                            + "   \n"
                            + "}"))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
    }

    @Test
    public void givenInvalidRequest_whenDelete_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletDeleteRequestBuilder("/videos/"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

    @Test
    public void givenValidRequest_whenDelete_returnOK() throws Exception {

        assertThat(
                mockMvc
                        .perform(servletDeleteRequestBuilder("/videos/t").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isAccepted()));
    }

    @Test
    public void givenValidRequest_whenGet_returnOK() throws Exception {
    VideoModel model = new VideoModel("videos", "my videos", "", Set.of(),Set.of());
        List<VideoModel> tags = new ArrayList<>();
        tags.add(model);

        when(videoService.getAllVideos("","")).thenReturn(tags);

        assertThat(
                mockMvc
                        .perform(servletRequestBuilder("/videos").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()));
    }
}
