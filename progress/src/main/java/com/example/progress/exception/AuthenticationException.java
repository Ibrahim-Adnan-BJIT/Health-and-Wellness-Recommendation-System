package com.example.progress.exception;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationException extends RuntimeException{
	private HttpStatus status;
    private String message;

}
