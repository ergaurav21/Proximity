package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.TagsModel;
import com.proximity.technicaltest.service.TagsService;
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

import static com.proximity.technicaltest.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TagsControllerTest {

  @Mock private TagsService tagsService;
  private MockMvc mockMvc;

  @Before
  public void setUp() {

    TagsController tagsController = new TagsController(tagsService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(tagsController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

  @Test
    public void givenInvalidRequest_whenCreate_returnError() throws Exception {

      assertThat(
              mockMvc.perform(servletPostRequestBuilder("/tags"))
                      .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
  }

    @Test
    public void givenValidRequest_whenCreate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPostRequestBuilder("/tags")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "    \"tagName\": \"English\",\n"
                            + "    \"description\": \"English subject\"\n"
                            + "}"))
            .andExpect(MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void givenInvalidRequest_whenUpdate_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletPutRequestBuilder("/tags"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

  @Test
  public void givenValidRequest_whenUpdate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPutRequestBuilder("/tags/t")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" + "    \"description\": \"English subject\"\n" + "}"))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
  }

    @Test
    public void givenInvalidRequest_whenDelete_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletDeleteRequestBuilder("/tags/"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

  @Test
  public void givenValidRequest_whenDelete_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(servletDeleteRequestBuilder("/tags/t").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
  }

  @Test
  public void givenValidRequest_whenGet_returnOK() throws Exception {
    TagsModel model = new TagsModel("tag", "my tag");
    List<TagsModel> tags = new ArrayList<>();
    tags.add(model);

    when(tagsService.getAllTags()).thenReturn(tags);

    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/tags").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()));
  }



}
