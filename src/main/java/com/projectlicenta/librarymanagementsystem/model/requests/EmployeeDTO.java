package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeeDTO extends UserDTO{

    @Nullable
    private Integer branchId;

    @Nullable
    @Size(min = 13, max = 13, message="Length must be exactly 13 characters")
    private String IDNP;

    @NotBlank(message = "Must not be blank")
    private String jobTitle;

    public EmployeeDTO(@NotBlank(message = "Must not be blank") @Email(message = "Must be valid email") @Length(min = 3, max = 64, message = "Length must be between 3 and 64") String email, @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Must contain at least 1 uppercase and at least 1 lowercase character") @NotBlank(message = "Must not be blank") @Length(min = 5, max = 32, message = "Length must be between 5 and 32") String password, @NotBlank(message = "Must not be blank") @Length(min = 2, message = "Length must be at least 2") String firstName, @NotBlank(message = "Must not be blank") @Length(min = 2, message = "Length must be at least 2") String lastName, @NotBlank(message = "Must not be blank") @Length(min = 2, message = "Length must be at least 2") String surName, @Length(min = 8, max = 13, message = "Length must be between 8 and 13") String phone, String icon, @NotBlank(message = "Must not be blank") String street, @NotBlank(message = "Must not be blank") String city, @NotBlank(message = "Must not be blank") String home, @NotBlank(message = "must not be blank") @Pattern(regexp = "user$|admin$|librarian$",
            message = "Please fill a valid type",
            flags = Pattern.Flag.CASE_INSENSITIVE) String type, @Nullable Integer branchId, @Nullable String IDNP, String jobTitle) {
        super(email, password, firstName, lastName, surName, phone, icon, street, city, home, type);
        this.branchId = branchId;
        this.IDNP = IDNP;
        this.jobTitle = jobTitle;
    }
}
