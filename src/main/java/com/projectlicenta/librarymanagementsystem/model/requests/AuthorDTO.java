package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class AuthorDTO {

    @NotBlank(message = "Must not be blank")
    private String lastName;

    @NotBlank(message = "Must not be blank")
    private String firstName;

    private String surName;
}
