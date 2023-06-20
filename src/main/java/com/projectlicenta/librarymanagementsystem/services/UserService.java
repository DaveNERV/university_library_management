package com.projectlicenta.librarymanagementsystem.services;


import com.projectlicenta.librarymanagementsystem.model.entities.Credential;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.requests.UserDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.JokeDTO;

import java.util.List;

public interface UserService {

    void deleteUser(User user);

    User updateUser(User findedUser, User user, String password);

    boolean existsUserByEmail(String email);

    User addUser(User user, String type, String password);

    User loadCurrentUser();
}
