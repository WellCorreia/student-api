package com.wellington.student.dto;

import com.sun.istack.NotNull;
import com.wellington.student.enums.GradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotNull
    @Size(min = 1,max = 200)
    private String name;

    @NotNull
    @Max(150)
    private int age;

    @NotNull
    @Size(min = 10,max = 10)
    private String registry;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private GradeType grade;
}
