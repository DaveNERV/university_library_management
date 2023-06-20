package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardReaderDTO {

    private Integer readerId;

    private String fullName;

    private String email;

    private String group;

    private String ticket;

    private String studyPeriod;

    private String phone;

    private String address;
}
