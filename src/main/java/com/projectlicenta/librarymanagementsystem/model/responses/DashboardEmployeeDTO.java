package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardEmployeeDTO {

    private Integer employeeId;

    private String fullName;

    private String jobTitle;

    private String email;

    private String IDNP;

    private String address;

    private String phone;
}
