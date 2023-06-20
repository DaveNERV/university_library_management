package com.projectlicenta.librarymanagementsystem.services.implementation;

import com.projectlicenta.librarymanagementsystem.exceptions.*;
import com.projectlicenta.librarymanagementsystem.model.entities.*;
import com.projectlicenta.librarymanagementsystem.model.responses.JokeDTO;
import com.projectlicenta.librarymanagementsystem.repository.*;
import com.projectlicenta.librarymanagementsystem.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final Environment environment;

    private final SessionRepository sessionRepository;

    private final PasswordEncoder encoder;

    private final CredentialRepository credentialRepository;

    private static final String ADMIN = "admin";

    private static final String LIBRARIAN = "librarian";

    private static final String USER = "user";

    @Override
    public void deleteUser(User user) {
        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            clear(user);
        } else {
            throw new DevProfileNotSetException("Wrong active profile");
        }
    }

    @Override
    public User addUser(User user, String type, String password) {
        Role role;
        if (ADMIN.equalsIgnoreCase(type)) {
            role = roleRepository.findByName("ROLE_ADMIN");
        } else if (LIBRARIAN.equalsIgnoreCase(type)) {
            role = roleRepository.findByName("ROLE_LIBRARIAN");
        } else if (USER.equalsIgnoreCase(type)) {
            role = roleRepository.findByName("ROLE_USER");
        }else{
            throw new InvalidUserRoleException("Invalid user role");
        }
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        Credential credential = new Credential(encoder.encode(password));

        user.setStatus(Status.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setRoleList(roles);
        user.setCredential(credential);

        credential.setUser(user);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User findedUser, User user, String password){
        if(!password.isEmpty() && password != null) {
            Credential credential = credentialRepository
                    .findCredentialByUserId(findedUser.getId()).
                    orElseThrow(() -> new InvalidCredentialsException("Credential was not found"));
            credential.setPassword(encoder.encode(password));
            credentialRepository.save(credential);
        }

        findedUser.setEmail(user.getEmail());
        findedUser.setFirstName(user.getFirstName());
        findedUser.setLastName(user.getLastName());
        findedUser.setSurName(user.getSurName());
        findedUser.setIcon(user.getIcon());
        findedUser.setPhoneNumber(user.getPhoneNumber());
        findedUser.setStatus(user.getStatus());

        findedUser.setUpdated(LocalDateTime.now());

        return userRepository.save(findedUser);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    private void clear(User user) {
        List<Session> list = sessionRepository.findAllByCredential(user.getCredential());

        sessionRepository.deleteAll(list);
        userRepository.delete(user);
    }

    @Override
    public User loadCurrentUser(){
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = details.getUsername();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " could not be found"));
        return user;
    }
}
