package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUserSearchInOffertResponseDTO {

    private Integer readerId;

    private String firstName;

    private String lastName;

    private String surName;

    private String ticket;
}
