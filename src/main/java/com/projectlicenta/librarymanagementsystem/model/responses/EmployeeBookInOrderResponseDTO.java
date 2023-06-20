package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmployeeBookInOrderResponseDTO {

    private BookUserResponseDTO bookUserResponseDTO;

    private Integer nr;

    private Integer price;

    private Boolean canceled;

    private String reader;
}
