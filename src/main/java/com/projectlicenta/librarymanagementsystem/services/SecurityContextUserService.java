package com.projectlicenta.librarymanagementsystem.services;


import com.projectlicenta.librarymanagementsystem.model.entities.User;

public interface SecurityContextUserService {
    User getUserFromSecurityContext();
}
