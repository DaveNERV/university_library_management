package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Getter
@Setter
public class ReaderDTO extends UserDTO{

    @Nullable
    @Length(max=10, message = "length must be fewer than 10")
    private String group;

    @Nullable
    @Length(min=1, max=20, message = "Length must be between 1 and 20")
    private String studyPeriod;
}
