package com.proximity.technicaltest.service;

import com.proximity.technicaltest.entity.Subject;
import com.proximity.technicaltest.exception.NotFoundException;
import com.proximity.technicaltest.model.SubjectModel;
import com.proximity.technicaltest.model.SubjectUpsertRequest;
import com.proximity.technicaltest.repository.SubjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {
  @Mock private SubjectRepository subjectRepository;
  @Mock private UserService userService;
  private SubjectService subjectService;

  @Before
  public void setup() {
    subjectService = new SubjectService(subjectRepository, userService);
  }

  @Test
   public void givenDuplicateSubject_whenCreate_thenThrowIllegalArgumentException(){
      //Given
      Subject subject = new Subject();
      subject.setSubjectName("hindi");

      when(subjectRepository.findBySubjectName(any())).thenReturn(subject);

      // When
      assertThatThrownBy(() -> subjectService.create(subject))
      // Then
     .isInstanceOf(IllegalArgumentException.class)
     .hasMessage("Duplicate Subject: hindi");
  }

    @Test
    public void givenValidRequest_whenCreate_thenSuccess(){
        //Given
        Subject subject = new Subject();
        subject.setSubjectName("hindi");

        when(subjectRepository.findBySubjectName(any())).thenReturn(null);

        //Then
        subjectService.create(subject);
    }

    @Test
    public void givenInvalidSubject_whenUpdate_thenThrowNotFoundException(){
        //Given
        SubjectUpsertRequest subject = new SubjectUpsertRequest();
        subject.setDescription("hindi subject");
        String subjectName = "hindi";

        when(subjectRepository.findBySubjectName(any())).thenReturn(null);

        // When
        assertThatThrownBy(() -> subjectService.update(subject,subjectName))
         // Then
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Subject not found: "+subjectName);
    }

    @Test
    public void givenValidRequest_whenUpdate_thenSuccess(){
        //Given
        SubjectUpsertRequest subject = new SubjectUpsertRequest();
        subject.setDescription("hindi subject");
        String subjectName = "hindi";
        Subject sub = new Subject();
        sub.setSubjectName(subjectName);
        sub.setDescription("test description");

        when(subjectRepository.findBySubjectName(any())).thenReturn(sub);

        // Then
        subjectService.update(subject,subjectName);

    }

    @Test
    public void givenValidRequest_whenGet_thenSuccess(){
        //Given
        Object[] object = new Object[2];
        object[0] = "Hindi";
        object[1] = "Hindi literature";

        List<Object[]> objects = new ArrayList<>();
        objects.add(object);

        when(subjectRepository.getSubjects()).thenReturn(objects);

        // When
        List<SubjectModel>  subjectModels = subjectService.getAllSubjects();

        //Then
        assertThat(subjectModels).isNotEmpty();
        assertEquals(subjectModels.size(), 1);
    }
}
