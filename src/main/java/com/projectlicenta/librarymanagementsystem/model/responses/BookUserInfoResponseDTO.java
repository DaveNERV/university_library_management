package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookUserInfoResponseDTO {

    private Integer bookId;

    private String bookName;

    private String ISBN;

    private String publishPlace;

    private Short publishYear;

    private String genreList;

    private String authorsList;

    private String publish;

    private String UDK;

    private String BBK;
}
