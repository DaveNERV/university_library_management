package com.projectlicenta.librarymanagementsystem.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
