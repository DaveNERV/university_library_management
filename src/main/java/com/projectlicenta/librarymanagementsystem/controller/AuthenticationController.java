package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.exceptions.InvalidCredentialsException;
import com.projectlicenta.librarymanagementsystem.model.requests.*;
import com.projectlicenta.librarymanagementsystem.model.responses.LoginResponse;
import com.projectlicenta.librarymanagementsystem.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register/save")
    public String signup(@ModelAttribute @Valid ReaderDTO request, Model model) {
        authenticationService.registerNewUser(request);
        return "security/account-created";
    }

    @GetMapping(value="/register")
    public String register(Model model) {
        model.addAttribute("newAccount", new ReaderDTO());
        return "security/register";
    }

    @GetMapping(value="/login")
    public String login(Model model) {
        model.addAttribute("loginUser", new LoginUserDTO());
        return "security/login";
    }
}
