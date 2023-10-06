package com.feedback.main.controller;

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
    public ResponseEntity<String> dietFeedbackDecisions() {
        String allDiets = dataDrivenDecisionService.dietFeedbackDecisions();
        return new  ResponseEntity<>(allDiets, HttpStatus.OK);
    }

    @GetMapping("/sleep")
    public ResponseEntity<String> sleepFeedbackDecisions(){
        String sleep = dataDrivenDecisionService.sleepFeedbackDecisions();
        return  new ResponseEntity<>(sleep, HttpStatus.OK);
    }

    @GetMapping("/exercise")
    public ResponseEntity<String> exerciseFeedbackDecisions(){
        String exercise = dataDrivenDecisionService.exerciseFeedbackDecisions();
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @GetMapping("/mental-health")
    public ResponseEntity<String> mentalHealthFeedbackDecisions(){
        String health = dataDrivenDecisionService.mentalHealthFeedbackDecisions();
        return new ResponseEntity<>(health, HttpStatus.OK);
    }

}
