package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateEmployeeElementDTO extends UserElementDTO {

    @NotNull
    private Integer employeeId;

    @Nullable
    private Integer addressId;

    @Nullable
    @Size(min = 13, max = 13, message="Length must be exactly 13 characters")
    private String IDNP;

    @NotBlank(message = "Must not be blank")
    private String jobTitle;
}
