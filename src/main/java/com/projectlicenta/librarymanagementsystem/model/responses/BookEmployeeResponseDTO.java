package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookEmployeeResponseDTO {

    private Integer bookId;

    private String bookName;

    private String ISBN;

    private String copyrightSign;

    private Integer nrBooks;

    private Integer price;

    private String publishPlace;

    private Short publishYear;

    private String genreList;

    private String authorsList;

    private String publish;

    private String UDK;

    private String BBK;
}
