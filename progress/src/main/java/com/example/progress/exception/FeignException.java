package com.example.progress.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeignException extends RuntimeException{
    private Date timestamp;
    private String message;
    private HttpStatus status;

}
