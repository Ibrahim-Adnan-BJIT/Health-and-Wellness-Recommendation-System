package com.example.progress.controller;

import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.service.FeedbackProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/feedback")
@RequiredArgsConstructor
public class FeedbackProxyController {
    private final FeedbackProxyService feedbackProxyService;

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
}
