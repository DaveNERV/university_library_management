package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.InvalidCredentialsException;
import com.projectlicenta.librarymanagementsystem.exceptions.InvalidTokenException;
import com.projectlicenta.librarymanagementsystem.model.entities.Credential;
import com.projectlicenta.librarymanagementsystem.model.entities.Session;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.responses.LoginResponse;
import com.projectlicenta.librarymanagementsystem.repository.CredentialRepository;
import com.projectlicenta.librarymanagementsystem.repository.SessionRepository;
import com.projectlicenta.librarymanagementsystem.repository.UserRepository;
import com.projectlicenta.librarymanagementsystem.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final CredentialRepository credentialRepository;


    public void validateCredentials(String email, String password) {
        Credential credential = credentialRepository
                .findCredentialByUserEmailContaining(email).
                orElseThrow(() -> new InvalidCredentialsException("Email or password is incorrect"));
        throw new InvalidCredentialsException("Email or password is incorrect");
    }
}
