package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBookOffertDTO {

    private Integer offerId;

    private String orderWithDate;

    private String bookName;

    private String authorsList;

    private Short publishYear;

    private String publish;

    private String offerDate;

    private String deadLine;

    private Integer nrExemplary;

    private String status;

    private String daysOffered;
}
