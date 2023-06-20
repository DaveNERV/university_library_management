package com.projectlicenta.librarymanagementsystem.model.requests;

import com.projectlicenta.librarymanagementsystem.model.entities.Status;
import com.projectlicenta.librarymanagementsystem.model.requests.validator.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserElementDTO {
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be valid email")
    @Length(min=3, max=64, message = "Length must be between 3 and 64")
    private String email;

    @NotBlank(message = "Must not be blank")
    @Length(min=2, message = "Length must be at least 2")
    private String firstName;

    @NotBlank(message = "Must not be blank")
    @Length(min=2, message = "Length must be at least 2")
    private String lastName;

    @NotBlank(message = "Must not be blank")
    @Length(min=2, message = "Length must be at least 2")
    private String surName;

    @Nullable
    @Length(min=8, max=13, message = "Length must be between 8 and 13")
    private String phone;

    @Nullable
    private String icon;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String role;

    @PasswordConstraint
    private String password;

    @NotBlank(message = "Must not be blank")
    private String street;

    @NotBlank(message = "Must not be blank")
    private String city;

    @NotBlank(message = "Must not be blank")
    private String house;
}
