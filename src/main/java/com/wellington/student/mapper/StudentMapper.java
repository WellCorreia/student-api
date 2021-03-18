package com.wellington.student.mapper;

import com.wellington.student.dto.StudentDTO;
import com.wellington.student.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student toModel(StudentDTO studentDTO);

    StudentDTO toDTO(Student student);
}
