package com.wellington.student.builder;

import com.wellington.student.dto.StudentDTO;
import com.wellington.student.enums.GradeType;
import lombok.Builder;

@Builder
public class StudentDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Wellington";

    @Builder.Default
    private String registry = "3214569877";

    @Builder.Default
    private int age = 25;

    @Builder.Default
    private GradeType grade = GradeType.FIRST;

    public StudentDTO toStudentDTO() {

        return new StudentDTO(
                id,
                name,
                age,
                registry,
                grade);
    }
}
