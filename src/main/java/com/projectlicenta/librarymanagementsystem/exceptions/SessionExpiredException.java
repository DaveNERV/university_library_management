package com.projectlicenta.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException(String message) {
        super(message);
    }
}
