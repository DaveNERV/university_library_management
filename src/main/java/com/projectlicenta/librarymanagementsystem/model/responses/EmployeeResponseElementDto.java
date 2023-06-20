package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseElementDto {

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private String surName;

    private String IDNP;

    private String jobTitle;

    private String email;

    private String phone;

    private String icon;

    private LocalDateTime created;

    private String status;

    private String role;

    private String city;

    private String house;

    private String street;
}
