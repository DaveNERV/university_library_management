package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UpdateExemplariesDTO {

    private List<String> inventaryNumbers;

    private String status;

    private Integer shell;

    private Integer branchId;
}
