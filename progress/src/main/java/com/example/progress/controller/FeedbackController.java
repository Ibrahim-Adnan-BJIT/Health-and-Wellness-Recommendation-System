package com.example.progress.controller;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    public ResponseEntity<?> addFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO) {

        return ResponseHandler.generateResponse(new Date(),"", HttpStatus.OK);
    }
}
