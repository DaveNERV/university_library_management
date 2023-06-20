package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    @NotBlank(message = "Must not be blank")
    @Email(message = "Must be valid email")
    @Length(min=3, max=64, message = "Length must be between 3 and 64")
    private String email;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "Must contain at least 1 uppercase and at least 1 lowercase character")
    @NotBlank(message = "Must not be blank")
    @Length(min=5, max=32, message = "Length must be between 5 and 32")
    private String password;
}

