package com.projectlicenta.librarymanagementsystem.controller;

import com.projectlicenta.librarymanagementsystem.exceptions.*;
import com.projectlicenta.librarymanagementsystem.model.requests.LoginUserDTO;
import com.projectlicenta.librarymanagementsystem.model.requests.ReaderDTO;
import com.projectlicenta.librarymanagementsystem.model.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String OUT_OF_STOCK = "Book is out o stock";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView methodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        String message = makeErrorMessage(errorList);
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(BindException.class)
    public ModelAndView bindExceptionHandler(HttpServletRequest request, BindException e) {
        List<FieldError> errorList = e.getFieldErrors();
        String message = makeErrorMessage(errorList);
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ModelAndView userAlreadyRegisteredHandler(HttpServletRequest request, UserAlreadyRegisteredException e) {
        ModelAndView modelAndView = new ModelAndView("security/register");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("newAccount", new ReaderDTO());
        return modelAndView;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView credentialValuesNotValidHandler(HttpServletRequest request, BadCredentialsException e) {
        System.out.println("ExceptionHandler called");
        ModelAndView modelAndView = new ModelAndView("security/login");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("loginUser", new LoginUserDTO());
        return modelAndView;
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ModelAndView authenticationNotCompleteHandler(HttpServletRequest request,
                                                   InsufficientAuthenticationException e,
                                                   Model model) {
        ModelAndView modelAndView = new ModelAndView("security/login");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("loginUser", new LoginUserDTO());
        return modelAndView;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView userFromContextNotFoundHandler(HttpServletRequest request, UsernameNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("security/login");
        modelAndView.addObject("errorMessage", e.getMessage());
        modelAndView.addObject("loginUser", new LoginUserDTO());
        return modelAndView;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ModelAndView employeeNotFoundHandler(HttpServletRequest request, EmployeeNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public ModelAndView readerNotFoundHandler(HttpServletRequest request, ReaderNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/404-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DevProfileNotSetException.class)
    public ModelAndView wrongProfileHandler(HttpServletRequest request, DevProfileNotSetException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/403-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ModelAndView userTypeIsNotValidHandler(HttpServletRequest request, InvalidUserRoleException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidBranchException.class)
    public ModelAndView invalidBranchHandler(HttpServletRequest request, InvalidBranchException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidAddressException.class)
    public ModelAndView invalidAdressHandler(HttpServletRequest request, InvalidAddressException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidGenreException.class)
    public ModelAndView invalidGenreHandler(HttpServletRequest request, InvalidGenreException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidPublishException.class)
    public ModelAndView invalidPublishHandler(HttpServletRequest request, InvalidPublishException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidAuthorException.class)
    public ModelAndView invalidAuthorHandler(HttpServletRequest request, InvalidAuthorException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ModelAndView genreNotFoundHandler(HttpServletRequest request, GenreNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/404-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ModelAndView authorNotFoundHandler(HttpServletRequest request, AuthorNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/404-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ModelAndView bookNotFoundHandler(HttpServletRequest request, BookNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("error-pages/404-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(InvalidBookException.class)
    public ModelAndView invalidBookHandler(HttpServletRequest request, InvalidBookException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentHandler(HttpServletRequest request, IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error-pages/400-error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    @ResponseBody
    @ExceptionHandler(BookOutOfStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> bookOutOfStockHandler(HttpServletRequest request,
                                                                         BookOutOfStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), OUT_OF_STOCK,
                        e.getMessage(), request.getServletPath()));

    }

    private String makeErrorMessage(List<FieldError> errorList) {
        StringBuilder builder = new StringBuilder();
        for (FieldError error : errorList) {
            builder.append("Field '")
                    .append(error.getField())
                    .append("' ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        return builder.toString();
    }

}
