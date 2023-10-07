package com.example.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPreference extends RuntimeException{
    public InvalidPreference(String message) {
        super(message);
    }
}
