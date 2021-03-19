package com.wellington.student.controller;

import com.wellington.student.builder.StudentDTOBuilder;
import com.wellington.student.dto.StudentDTO;
import com.wellington.student.entity.Student;
import com.wellington.student.exception.StudentAlreadyRegisteredException;
import com.wellington.student.exception.StudentNotFoundException;
import com.wellington.student.mapper.StudentMapper;
import com.wellington.student.service.StudentService;
import com.wellington.student.utils.JsonConvertionsUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
    private static final String STUDENT_API_URL_PATH = "/api/v1/students";
    private static final long VALID_STUDENT_ID = 1L;
    private static final long INVALID_STUDENT_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locate) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPostIsCalledThenTheStudentIsCreated() throws Exception {
        // Given
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();

        // When
        Mockito.when(studentService.create(studentDTO)).thenReturn(studentDTO);

        // Then
        mockMvc.perform(post(STUDENT_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonConvertionsUtils.asJsonSting(studentDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", Matchers.is(studentDTO.getName())))
            .andExpect(jsonPath("$.age", Matchers.is(studentDTO.getAge())))
            .andExpect(jsonPath("$.registry", Matchers.is(studentDTO.getRegistry())));

    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // Given
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();
        studentDTO.setName("");

        // Then
        mockMvc.perform(post(STUDENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionsUtils.asJsonSting(studentDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGETIsCalledWithValidRegistryThenOkStatusIsReturned() throws Exception {
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();

        // When
        Mockito.when(studentService.findByRegistry(studentDTO.getRegistry())).thenReturn(studentDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_API_URL_PATH + "/" + studentDTO.getRegistry())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(studentDTO.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(studentDTO.getAge())))
                .andExpect(jsonPath("$.registry", Matchers.is(studentDTO.getRegistry())));
    }
    @Test
    void whenGETIsCalledWithInvalidRegistryThenNotFoundStatusIsReturned() throws Exception {
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();

        // When
        Mockito.when(studentService.findByRegistry(studentDTO.getRegistry())).thenThrow(StudentNotFoundException.class);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_API_URL_PATH + "/" + studentDTO.getRegistry())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETAllWithStudentsIsCalledThenOkStatusIsReturned() throws Exception {
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();

        Mockito.when(studentService.findAll()).thenReturn(Collections.singletonList(studentDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Matchers.is(studentDTO.getName())))
                .andExpect(jsonPath("$[0].age", Matchers.is(studentDTO.getAge())))
                .andExpect(jsonPath("$[0].registry", Matchers.is(studentDTO.getRegistry())));
    }

    @Test
    void whenGETAllWithoutStudentsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();


        //when
        Mockito.when(studentService.findAll()).thenReturn(Collections.singletonList(studentDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();

        //when
        Mockito.doNothing().when(studentService).delete(studentDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STUDENT_API_URL_PATH + "/" + studentDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        Mockito.doThrow(StudentNotFoundException.class).when(studentService).delete(INVALID_STUDENT_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(STUDENT_API_URL_PATH + "/" + INVALID_STUDENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPUTIsCalledToIncrementDiscountThenOKStatusIsReturned() throws Exception {
        // Given
        StudentDTOBuilder studentDTOBuilder = new StudentDTOBuilder();
        StudentDTO studentDTO = studentDTOBuilder.toStudentDTO();
        int age = 5;
        studentDTO.setAge(age);

        // When
        Mockito.when(studentService.update(studentDTO.getId(),studentDTO)).thenReturn(studentDTO);

        // Then
        mockMvc.perform(put(STUDENT_API_URL_PATH + "/" + studentDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionsUtils.asJsonSting(studentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(studentDTO.getName())))
                .andExpect(jsonPath("$.age", Matchers.is(age)))
                .andExpect(jsonPath("$.registry", Matchers.is(studentDTO.getRegistry())));
    }
}
