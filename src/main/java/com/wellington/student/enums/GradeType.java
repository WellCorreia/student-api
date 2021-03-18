package com.wellington.student.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GradeType {

    FIRST("1st"),
    SECOND("2nd"),
    THIRD("3rd"),
    FOURTH("4th"),
    FIVETY("5th");

    private final String description;

}
