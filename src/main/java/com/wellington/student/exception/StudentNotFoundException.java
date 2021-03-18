package com.wellington.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends Exception{

    public StudentNotFoundException(String registry) {
        super(String.format("Student with registry %s not found in the system.", registry));
    }
}
