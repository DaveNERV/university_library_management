package com.projectlicenta.librarymanagementsystem.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferBookDTO {

    private List<Integer> bookIds;

    private Integer order;
}
