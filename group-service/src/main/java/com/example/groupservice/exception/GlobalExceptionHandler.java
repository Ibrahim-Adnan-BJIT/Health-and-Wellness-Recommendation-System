package com.example.groupservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GroupNotExists.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleGroupNotExists(GroupNotExists groupNotExists,
                                                                     WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                groupNotExists.getMessage(),
                webRequest.getDescription(false),
                "Group Details are Invalid"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostIdNotExists.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handlePostIdNotExists(PostIdNotExists postIdNotExists,
                                                                     WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                postIdNotExists.getMessage(),
                webRequest.getDescription(false),
                "Post cant be null"
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNull.class) //It used in specific Exception
    public ResponseEntity<ExceptionDetails> handleCommentNull(CommentNull commentNull,
                                                                  WebRequest webRequest)
    {
        ExceptionDetails exceptionDetails=new ExceptionDetails(
                LocalDateTime.now(),
                commentNull.getMessage(),
                webRequest.getDescription(false),
                "Comment cant be Null"
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
