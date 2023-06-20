package com.projectlicenta.librarymanagementsystem.services;

import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.requests.LoginUserDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UserDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.LoginResponse;

public interface AuthenticationService {
    void registerNewUser(ReaderDTO request);
    //void loginUser(LoginUserDTO request);
}
