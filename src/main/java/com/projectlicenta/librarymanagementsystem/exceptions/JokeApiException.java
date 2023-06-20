package com.projectlicenta.librarymanagementsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class JokeApiException extends RuntimeException{
    public JokeApiException(String message) {
        super(message);
    }
}
