package com.projectlicenta.librarymanagementsystem.security.config;

import com.projectlicenta.librarymanagementsystem.model.requests.LoginUserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        System.out.println("handler was reached");
        if (exception instanceof BadCredentialsException) {
            System.out.println("handler was reached");
            errorMessage = "Emailul sau parola incorectă";
        } else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Insufficient authentication.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Utilizatirul nu a fost găsit";
        } else {
            errorMessage = "Authentication failed.";
        }

        String loginPageUrl = request.getContextPath() + "/login?error=" + errorMessage;
        response.sendRedirect(loginPageUrl);
    }
}