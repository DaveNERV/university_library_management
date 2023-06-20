package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateReaderElementDTO extends UserElementDTO {
    @NotNull
    private Integer readerId;
    @NotBlank(message = "Must not be blank")
    @Length(min=2, max=10, message = "Length must be between 2 and 10")
    private String ticket;

    @Nullable
    @Length(max=10, message = "length must be fewer than 10")
    private String group;

    @NotBlank(message = "Must not be blank")
    @Length(min=1, max=20, message = "Length must be between 1 and 20")
    private String studyPeriod;
}
