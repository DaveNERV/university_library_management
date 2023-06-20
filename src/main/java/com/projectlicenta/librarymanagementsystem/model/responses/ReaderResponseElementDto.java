package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderResponseElementDto {

    private Integer readerId;

    private String firstName;

    private String lastName;

    private String surName;

    private String email;

    private String group;

    private String ticket;

    private String studyPeriod;

    private String icon;

    private String city;

    private String house;

    private String street;
}
