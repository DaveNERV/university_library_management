package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthorListDTO {

    private Integer authorId;

    private String lastName;

    private String firstName;

    private String surName;
}
