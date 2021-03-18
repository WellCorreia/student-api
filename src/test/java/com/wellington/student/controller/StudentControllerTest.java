package com.wellington.student.controller;

import com.wellington.student.builder.StudentDTOBuilder;
import com.wellington.student.dto.StudentDTO;
import com.wellington.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
    private static final String STUDENT_API_URL_PATH = "/api/v1/students";

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

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
    void whenPostIsCalledThenTheStudentIsCreated() {
        // Given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
    }
}
