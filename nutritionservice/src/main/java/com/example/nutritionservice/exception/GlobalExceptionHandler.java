package com.example.nutritionservice.exception;

import com.example.nutritionservice.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex, WebRequest webRequest) {

        return ResponseHandler.generateResponse(ex.getTimestamp(), ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = error.getField().toUpperCase()
                    + ": " + error.getDefaultMessage();
            errorMessage.add(message);
        });
        return ResponseHandler.generateResponse(new Date(), "Request Failed",
                HttpStatus.BAD_REQUEST, errorMessage);
    }
}
