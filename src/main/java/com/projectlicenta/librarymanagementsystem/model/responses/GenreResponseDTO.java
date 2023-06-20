package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
public class GenreResponseDTO {

    private Integer genreId;

    private String genreName;
}
