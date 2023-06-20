package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOrderUserResponseDTO {

    private Integer orderId;

    private String status;

    private String orderDate;

    private String responsible;

    private String price;

    private String reader;
}
