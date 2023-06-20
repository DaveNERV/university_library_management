package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookUpdateResponseDTO {

    private Integer bookId;

    private String bookName;

    private String publishPlace;

    private Short publishYear;

    private String photo;

    private List<GenreResponseDTO> genreList;

    private List<AuthorResponseDTO> authorsList;

    private String publishName;

    private String ISBN;

    private String UDK;

    private String BBK;

    private Integer number;

    private Integer price;
}
