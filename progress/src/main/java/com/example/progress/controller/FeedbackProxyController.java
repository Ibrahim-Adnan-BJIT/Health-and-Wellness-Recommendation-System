package com.example.progress.controller;

import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.service.FeedbackProxyService;
import com.example.progress.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/feedback")
@RequiredArgsConstructor
public class FeedbackProxyController {
    private final FeedbackProxyService feedbackProxyService;
    private final FeedbackService feedbackService;

    @GetMapping("/sleep/get")
    public List<AllFeedbackResponseDTO> getSleepFeedback() {
        return feedbackProxyService.getSleepFeedback();
    }

    @GetMapping("/diet/get")
    public List<AllFeedbackResponseDTO> getDietFeedback() {

        return feedbackProxyService.getDietFeedback();
    }

    @GetMapping("/exercise/get")
    public List<AllFeedbackResponseDTO> getExerciseFeedback() {

        return feedbackProxyService.getExerciseFeedback();
    }

    @GetMapping("/mental-health/get")
    public List<AllFeedbackResponseDTO> getMentalHealthFeedback() {

        return feedbackProxyService.getMentalHealthFeedback();
    }

    @GetMapping("/get/user/{userId}")
    public FeedbackResponseDTO getUserFeedback(@PathVariable long userId) {

        return feedbackProxyService.getFeedbackByUser(userId);
    }
}
