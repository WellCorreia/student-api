package com.wellington.student.service;

import com.wellington.student.builder.StudentDTOBuilder;
import com.wellington.student.dto.StudentDTO;
import com.wellington.student.entity.Student;
import com.wellington.student.exception.StudentAlreadyRegisteredException;
import com.wellington.student.exception.StudentNotFoundException;
import com.wellington.student.mapper.StudentMapper;
import com.wellington.student.repository.StudentRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    @InjectMocks
    private StudentService studentService;

    @Test
    void whenStudentInformedThenItShouldBeCreated() throws StudentAlreadyRegisteredException {
        // Given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
        Student student = studentMapper.toModel(studentDTO);

        // When
        Mockito.when(studentRepository.findByRegistry(studentDTO.getRegistry())).thenReturn(Optional.empty());
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        // Then
        StudentDTO studentDTOCreated = studentService.create(studentDTO);

        // Matcher
        MatcherAssert.assertThat(studentDTOCreated.getId(), Matchers.is(Matchers.equalTo(studentDTO.getId())));
        MatcherAssert.assertThat(studentDTOCreated.getRegistry(), Matchers.is(Matchers.equalTo(studentDTO.getRegistry())));
        MatcherAssert.assertThat(studentDTOCreated.getName(), Matchers.is(Matchers.equalTo(studentDTO.getName())));
        MatcherAssert.assertThat(studentDTOCreated.getGrade(), Matchers.is(Matchers.equalTo(studentDTO.getGrade())));
    }
    @Test
    void whenStudentAlreadyCreatedThenItNotShouldBeCreated() {
        // Given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
        Student student = studentMapper.toModel(studentDTO);

        // When
        Mockito.when(studentRepository.findByRegistry(studentDTO.getRegistry())).thenReturn(Optional.of(student));

        // Then
        assertThrows(StudentAlreadyRegisteredException.class, () -> studentService.create(studentDTO));
    }

    @Test
    void whenListAllStudentIsCalledThenReturnAListOfStudents() {
        // Given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
        Student student = studentMapper.toModel(studentDTO);

        // When
        Mockito.when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));

        // Then
        List<Student> fouStudentList = studentRepository.findAll();
        MatcherAssert.assertThat(fouStudentList, Matchers.is(Matchers.not(Matchers.empty())));
        MatcherAssert.assertThat(fouStudentList.get(0), Matchers.is(Matchers.equalTo(student)));

    }

    @Test
    void whenFoundAllStudentIsCalledThenReturnAnEmptyOfStudents() {
        // When
        Mockito.when(studentRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // Then
        List<Student> fouStudentList = studentRepository.findAll();
        MatcherAssert.assertThat(fouStudentList, Matchers.is(Matchers.empty()));
    }

    @Test
    void whenDeleteStudentIsCalledThenAStudentShouldBeDelete() throws StudentNotFoundException {
        //given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
        Student student = studentMapper.toModel(studentDTO);

        //when
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Mockito.doNothing().when(studentRepository).deleteById(studentDTO.getId());

        //then
        studentService.delete(studentDTO.getId());

        verify(studentRepository, Mockito.times(1)).findById(studentDTO.getId());
        verify(studentRepository, Mockito.times(1)).deleteById(studentDTO.getId());
    }

    @Test
    void whenUpdateStudentIsCalledThenAStudentShouldBeUpdated() throws StudentNotFoundException {
        //given
        StudentDTO studentDTO = StudentDTOBuilder.builder().build().toStudentDTO();
        Student student = studentMapper.toModel(studentDTO);

        // When
        Mockito.when(studentRepository.findById(studentDTO.getId())).thenReturn(Optional.of(student));
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        // Then
        int age = 65;
        studentDTO.setAge(age);

        StudentDTO studentDTOUpdated = studentService.update(studentDTO.getId(), studentDTO);

        MatcherAssert.assertThat(student.getAge(), Matchers.lessThan(studentDTOUpdated.getAge()));
    }
}
