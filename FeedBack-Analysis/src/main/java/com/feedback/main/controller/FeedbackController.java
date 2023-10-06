package com.feedback.main.controller;

import com.feedback.main.model.AllFeedbackResponseDTO;
import com.feedback.main.model.FeedbackResponseDTO;
import com.feedback.main.service.FeedbackAnalysisDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
public class FeedbackController {
    @Autowired
    private FeedbackAnalysisDashboardService feedbackAnalysisDashboardService;

    @GetMapping("/feedback/diet")
    public ResponseEntity<List<AllFeedbackResponseDTO>> getAllFeedbackFromDiet() {
        List<AllFeedbackResponseDTO> feedbackList = feedbackAnalysisDashboardService.getAllFeedbackFromDiet();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

    @GetMapping("/feedback/sleep")
    public ResponseEntity<List<AllFeedbackResponseDTO>> getAllFeedbackFromSleep() {
        List<AllFeedbackResponseDTO> feedbackList = feedbackAnalysisDashboardService.getAllFeedbackFromSleep();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }


    @GetMapping("/feedback/exercise")
    public ResponseEntity<List<AllFeedbackResponseDTO>> getAllFeedbackFromExercises() {
        List<AllFeedbackResponseDTO> feedbackList = feedbackAnalysisDashboardService.getAllFeedbackFromExercises();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

    @GetMapping("/feedback/mental-health")
    public ResponseEntity<List<AllFeedbackResponseDTO>> getAllFeedbackFromMentalHealth() {
        List<AllFeedbackResponseDTO> feedbackList = feedbackAnalysisDashboardService.getAllFeedbackFromMentalHealth();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

    @GetMapping("/feedback/all/{userId}")
    public ResponseEntity<FeedbackResponseDTO> getAllFeedbackByUserId(@PathVariable Long userId) {
        FeedbackResponseDTO allFeedbackResponseDTO = feedbackAnalysisDashboardService.getAllFeedbackByUserId(userId);
        return new ResponseEntity<>(allFeedbackResponseDTO, HttpStatus.OK);
    }
}
