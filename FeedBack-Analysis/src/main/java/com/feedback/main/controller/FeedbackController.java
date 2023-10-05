package com.feedback.main.controller;

import com.feedback.main.model.AllFeedbackResponseDTO;
import com.feedback.main.model.FeedbackResponseDTO;
import com.feedback.main.service.FeedbackAnalysisDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class FeedbackController {
    @Autowired
    private FeedbackAnalysisDashboardService feedbackAnalysisDashboardService;

    @GetMapping("/feedback/diet")
    public List<AllFeedbackResponseDTO> getAllFeedbackFromDiet() {
        return feedbackAnalysisDashboardService.getAllFeedbackFromDiet();
    }

    @GetMapping("/feedback/sleep")
    public List<AllFeedbackResponseDTO> getAllFeedbackFromSleep() {
        return feedbackAnalysisDashboardService.getAllFeedbackFromSleep();
    }


    @GetMapping("/feedback/exercise")
    public List<AllFeedbackResponseDTO> getAllFeedbackFromExercises() {
        return feedbackAnalysisDashboardService.getAllFeedbackFromExercises();
    }

    @GetMapping("/feedback/mental-health")
    public List<AllFeedbackResponseDTO> getAllFeedbackFromMentalHealth() {
        return feedbackAnalysisDashboardService.getAllFeedbackFromMentalHealth();
    }

    @GetMapping("/feedback/all/{userId}")
    public FeedbackResponseDTO getAllFeedbackByUserId(@PathVariable Long userId) {
        return feedbackAnalysisDashboardService.getAllFeedbackByUserId(userId);
    }
}
