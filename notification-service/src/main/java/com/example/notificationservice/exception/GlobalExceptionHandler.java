package com.example.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EventNotFoundException.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleGroupNotExists(EventNotFoundException eventNotFoundException,
                                                                     WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                eventNotFoundException.getMessage(),
                webRequest.getDescription(false),
                "Event Details are Invalid"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPreference.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handlePostIdNotExists(InvalidPreference invalidPreference,
                                                                     WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                invalidPreference.getMessage(),
                webRequest.getDescription(false),
                "Preference cant be null"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(AuthenticationException exception,
                                                                            WebRequest webRequest){
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "Authentication Failed"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

}
