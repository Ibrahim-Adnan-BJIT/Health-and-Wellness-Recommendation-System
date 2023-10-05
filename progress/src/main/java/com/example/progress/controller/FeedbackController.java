package com.example.progress.controller;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.response.ResponseHandler;
import com.example.progress.service.AuthenticationService;
import com.example.progress.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final AuthenticationService authenticationService;

    @PostMapping("/diet/add")
    public ResponseEntity<?> addDietFeedback(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        long userId = authenticationService.getAuthenticatedUser();
        feedbackService.addDietFeedback(userId, feedbackRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "feedback added", HttpStatus.OK);
    }

    @PostMapping("/exercise/add")
    public ResponseEntity<?> addExerciseFeedback(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        long userId = authenticationService.getAuthenticatedUser();
        feedbackService.addExerciseFeedback(userId, feedbackRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "feedback added", HttpStatus.OK);
    }

    @PostMapping("/sleep/add")
    public ResponseEntity<?> addSleepFeedback(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        long userId = authenticationService.getAuthenticatedUser();
        feedbackService.addSleepFeedback(userId, feedbackRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "feedback added", HttpStatus.OK);
    }

    @PostMapping("/mental-health/add")
    public ResponseEntity<?> addMentalHealthFeedback(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        long userId = authenticationService.getAuthenticatedUser();
        feedbackService.addMentalHealthFeedback(userId, feedbackRequestDTO);
        return ResponseHandler.generateResponse(new Date(), "feedback added", HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserFeedback() {
        long userId = authenticationService.getAuthenticatedUser();
        return ResponseHandler.generateResponse(new Date(), "User Feedback", HttpStatus.OK,
                feedbackService.getFeedbackByUser(userId));
    }
}
