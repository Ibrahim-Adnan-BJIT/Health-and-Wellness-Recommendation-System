package com.feedback.main.controller;

import com.feedback.main.dietResponse.DietFeedbackDecisionResponse;
import com.feedback.main.service.DataDrivenDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/decision")
public class DataDrivenController {

    @Autowired
    private DataDrivenDecisionService dataDrivenDecisionService;

    @GetMapping("/diet")
    public ResponseEntity<DietFeedbackDecisionResponse> dietFeedbackDecisions() {
        DietFeedbackDecisionResponse response = dataDrivenDecisionService.dietFeedbackDecisions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sleep")
    public ResponseEntity<DietFeedbackDecisionResponse> sleepFeedbackDecisions() {
        DietFeedbackDecisionResponse response = dataDrivenDecisionService.sleepFeedbackDecisions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/exercise")
    public ResponseEntity<DietFeedbackDecisionResponse> exerciseFeedbackDecisions(){
        DietFeedbackDecisionResponse exercise = dataDrivenDecisionService.exerciseFeedbackDecisions();
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @GetMapping("/mental-health")
    public ResponseEntity<DietFeedbackDecisionResponse> mentalHealthFeedbackDecisions(){
        DietFeedbackDecisionResponse health = dataDrivenDecisionService.mentalHealthFeedbackDecisions();
        return new ResponseEntity<>(health, HttpStatus.OK);
    }

}
