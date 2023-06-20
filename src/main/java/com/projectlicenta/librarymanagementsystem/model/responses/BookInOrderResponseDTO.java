package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInOrderResponseDTO {

    private BookUserResponseDTO bookDTO;

    private Integer nr;

    private Integer price;

    private Boolean canceled;
}
