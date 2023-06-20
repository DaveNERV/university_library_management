package com.projectlicenta.librarymanagementsystem.services.mapper;

import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateEmployeeElementDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UpdateReaderElementDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UserDTO;

public class UserMapper {

    public static User toUser(UserDTO readerDTO) {

        return User.builder()
                .email(readerDTO.getEmail())
                .firstName(readerDTO.getFirstName())
                .lastName(readerDTO.getLastName())
                .surName(readerDTO.getSurName())
                .icon(readerDTO.getIcon())
                .phoneNumber(readerDTO.getPhone())
                .build();
    }

    public static User toUser(UpdateReaderElementDTO readerDTO) {

        return User.builder()
                .email(readerDTO.getEmail())
                .firstName(readerDTO.getFirstName())
                .lastName(readerDTO.getLastName())
                .surName(readerDTO.getSurName())
                .icon(readerDTO.getIcon())
                .phoneNumber(readerDTO.getPhone())
                .build();
    }

    public static User toUser(UpdateEmployeeElementDTO employeeDTO) {

        return User.builder()
                .email(employeeDTO.getEmail())
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .surName(employeeDTO.getSurName())
                .icon(employeeDTO.getIcon())
                .phoneNumber(employeeDTO.getPhone())
                .build();
    }
}
