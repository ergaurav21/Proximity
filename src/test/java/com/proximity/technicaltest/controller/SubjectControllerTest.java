package com.proximity.technicaltest.controller;


import com.proximity.technicaltest.model.SubjectModel;
import com.proximity.technicaltest.service.SubjectService;
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
public class SubjectControllerTest {

  @Mock private SubjectService subjectsService;
  private MockMvc mockMvc;

  @Before
  public void setUp() {

    SubjectController subjectsController = new SubjectController(subjectsService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(subjectsController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
  }

  @Test
    public void givenInvalidRequest_whenCreate_returnError() throws Exception {

      assertThat(
              mockMvc.perform(servletPostRequestBuilder("/subjectss"))
                      .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
  }

    @Test
    public void givenValidRequest_whenCreate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPostRequestBuilder("/subjects")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        "{\n"
                            + "    \"subjectName\": \"English\",\n"
                            + "    \"description\": \"English subjects\"\n"
                            + "}"))
            .andExpect(MockMvcResultMatchers.status().isCreated()));
    }

    @Test
    public void givenInvalidRequest_whenUpdate_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletPutRequestBuilder("/subjects"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

  @Test
  public void givenValidRequest_whenUpdate_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(
                servletPutRequestBuilder("/subjects/t")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n" + "    \"description\": \"English subjects\"\n" + "}"))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
  }

    @Test
    public void givenInvalidRequest_whenDelete_returnError() throws Exception {

        assertThat(
                mockMvc.perform(servletDeleteRequestBuilder("/subjects/"))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError()));
    }

  @Test
  public void givenValidRequest_whenDelete_returnOK() throws Exception {

    assertThat(
        mockMvc
            .perform(servletDeleteRequestBuilder("/subjects/t").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
  }

  @Test
  public void givenValidRequest_whenGet_returnOK() throws Exception {
    SubjectModel model = new SubjectModel("tag", "my tag");
    List<SubjectModel> subjects = new ArrayList<>();
    subjects.add(model);

    when(subjectsService.getAllSubjects()).thenReturn(subjects);

    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/subjects").contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()));
  }



}
