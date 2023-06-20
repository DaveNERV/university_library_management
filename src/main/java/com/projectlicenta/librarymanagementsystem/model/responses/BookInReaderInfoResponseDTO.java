package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInReaderInfoResponseDTO {

    private BookUserInfoResponseDTO bookDTO;

    private Integer nr;

    private String offerDate;

    private String deadLine;

    private String daysOffered;
}
