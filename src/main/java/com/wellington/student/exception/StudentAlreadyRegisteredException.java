package com.wellington.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentAlreadyRegisteredException extends Exception {


    public StudentAlreadyRegisteredException(String registry) {
        super(String.format("Student with registry %s already registered in the system.", registry));
    }
}
