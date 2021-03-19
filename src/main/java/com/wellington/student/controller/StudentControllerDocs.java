package com.wellington.student.controller;

import com.wellington.student.dto.StudentDTO;
import com.wellington.student.exception.StudentAlreadyRegisteredException;
import com.wellington.student.exception.StudentNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages Student")
public interface StudentControllerDocs {

    @ApiOperation(value = "Student creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success Student creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    StudentDTO createBeer(StudentDTO studentDTO) throws StudentAlreadyRegisteredException;

    @ApiOperation(value = "Returns beer found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success student found in the system"),
            @ApiResponse(code = 404, message = "Student with given name not found.")
    })
    StudentDTO findByName(@PathVariable String name) throws StudentNotFoundException;

    @ApiOperation(value = "Returns a list of all students registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all students registered in the system"),
    })
    List<StudentDTO> listBeers();

    @ApiOperation(value = "Delete a student found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success student deleted in the system"),
            @ApiResponse(code = 404, message = "Student with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws StudentNotFoundException;
}
