package com.projectlicenta.librarymanagementsystem.services;


import com.projectlicenta.librarymanagementsystem.model.requests.UserDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.LoginResponse;

public interface SessionService {

    void validateCredentials(String email, String password);
}
