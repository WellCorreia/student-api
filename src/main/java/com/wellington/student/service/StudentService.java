package com.wellington.student.service;

import com.wellington.student.dto.StudentDTO;
import com.wellington.student.entity.Student;
import com.wellington.student.exception.StudentAlreadyRegisteredException;
import com.wellington.student.exception.StudentNotFoundException;
import com.wellington.student.mapper.StudentMapper;
import com.wellington.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;

    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Student findById (Long id) throws StudentNotFoundException {
        Student foundStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return foundStudent;
    }

    public StudentDTO create(StudentDTO studentDTO) throws StudentAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(studentDTO.getRegistry());
        Student student = studentMapper.toModel(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toDTO(student);
    }

    public StudentDTO update(Long id, StudentDTO studentDTO) throws StudentNotFoundException {
        findById(id);
        Student student = studentMapper.toModel(studentDTO);
        student.setId(id);
        Student studentUpdated = studentRepository.save(student);
        return studentMapper.toDTO(studentUpdated);
    }

    public StudentDTO findByRegistry(String registry) throws StudentNotFoundException {
        Student foundStudent = studentRepository.findByRegistry(registry)
                .orElseThrow(() -> new StudentNotFoundException(registry));
        return studentMapper.toDTO(foundStudent);
    }

    public void verifyIfIsAlreadyRegistered(String registry) throws StudentAlreadyRegisteredException {
        Optional<Student> student = studentRepository.findByRegistry(registry);
        if(student.isPresent()) {
            throw new StudentAlreadyRegisteredException(registry);
        }
    }

    public void delete(Long id) throws StudentNotFoundException {
        findById(id);
        studentRepository.deleteById(id);
    }
}
