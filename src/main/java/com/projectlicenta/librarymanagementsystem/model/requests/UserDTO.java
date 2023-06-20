package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be valid email")
    @Length(min=3, max=64, message = "Length must be between 3 and 64")
    private String email;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Must contain at least 1 uppercase and at least 1 lowercase character")
    @NotBlank(message = "Must not be blank")
    @Length(min=5, max=32, message = "Length must be between 5 and 32")
    private String password;

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

    @NotBlank(message = "Must not be blank")
    private String street;

    @NotBlank(message = "Must not be blank")
    private String city;

    @NotBlank(message = "Must not be blank")
    private String house;

    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "user$|admin$|librarian$",
            message = "Please fill a valid type",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String type;
}
