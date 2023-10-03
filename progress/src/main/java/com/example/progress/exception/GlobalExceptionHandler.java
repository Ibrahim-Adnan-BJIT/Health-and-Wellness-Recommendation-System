package com.example.progress.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.progress.response.ResponseHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleResourceNotFoundException(CustomException ex, WebRequest webRequest) {

		return ResponseHandler.generateResponse(ex.getTimestamp(), ex.getMessage(), ex.getDetails(), ex.getStatus());
	}
}
