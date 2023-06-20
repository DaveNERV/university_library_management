package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.UserAlreadyRegisteredException;
import com.projectlicenta.librarymanagementsystem.model.entities.Credential;
import com.projectlicenta.librarymanagementsystem.model.entities.User;
import com.projectlicenta.librarymanagementsystem.model.requests.LoginUserDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.UserDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.LoginResponse;
import com.projectlicenta.librarymanagementsystem.services.AuthenticationService;
import com.projectlicenta.librarymanagementsystem.services.ReaderService;
import com.projectlicenta.librarymanagementsystem.services.SessionService;
import com.projectlicenta.librarymanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.projectlicenta.librarymanagementsystem.services.mapper.AddressMapper.toAddress;
import static com.projectlicenta.librarymanagementsystem.services.mapper.ReaderMapper.toReader;
import static com.projectlicenta.librarymanagementsystem.services.mapper.UserMapper.toUser;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final ReaderService readerService;

    @Override
    public void registerNewUser(ReaderDTO request) {
        String email = request.getEmail();
        if (userService.existsUserByEmail(email)){
            throw new UserAlreadyRegisteredException("Email: " + email + " is already registered.");
        }
        readerService.addReader(toReader(request), toUser(request), request.getType(), request.getPassword(), toAddress(request));
    }
}
