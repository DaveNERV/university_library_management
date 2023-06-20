package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookDTO {

    @NotBlank(message = "Must not be blank")
    private String bookName;

    @NotBlank(message = "Must not be blank")
    private String ISBN;

    @NotBlank(message = "Must not be blank")
    private String publishPlace;

    @NotNull
    private Short publishYear;

    private String photo;

    @NotNull
    private List<Integer> genreList;

    @NotNull
    private List<Integer> authorsList;

    @NotBlank(message = "Must not be blank")
    private String publishName;

    private String UDK;

    private String BBK;

    @NotNull
    private Integer number;

    @NotNull
    private Integer price;
}
