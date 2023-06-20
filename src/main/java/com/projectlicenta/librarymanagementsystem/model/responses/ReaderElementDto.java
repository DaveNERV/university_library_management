package com.projectlicenta.librarymanagementsystem.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderElementDto {

    private Integer readerId;

    private String firstName;

    private String lastName;

    private String surName;

    private String email;

    private String group;

    private String ticket;

    private String studyPeriod;

    private String phone;

    private String status;

    private String role;

    private String icon;

    private LocalDateTime created;

    private String city;

    private String house;

    private String street;
}
