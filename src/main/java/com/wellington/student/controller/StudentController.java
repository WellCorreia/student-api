package com.wellington.student.controller;

import com.wellington.student.dto.StudentDTO;
import com.wellington.student.exception.StudentAlreadyRegisteredException;
import com.wellington.student.exception.StudentNotFoundException;
import com.wellington.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{registry}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO find(@PathVariable String registry) throws StudentNotFoundException {
        return studentService.findByRegistry(registry);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> findAll() {
        return studentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create(@RequestBody @Valid StudentDTO studentDTO) throws StudentAlreadyRegisteredException {
        System.out.println(studentDTO);
        return studentService.create(studentDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO update(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO) throws StudentNotFoundException {
        return studentService.update(id, studentDTO);
    }

    @DeleteMapping("/{id")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws StudentNotFoundException {
        studentService.delete(id);
    }


}
