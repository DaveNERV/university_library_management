package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String surName;

    private String jobTitle;

    private String email;

    private String icon;

    private String city;

    private String house;

    private String street;
}
