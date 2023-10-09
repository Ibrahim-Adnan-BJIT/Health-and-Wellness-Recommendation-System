package com.example.recommendationservice.controller;

import com.example.recommendationservice.DTO.response.DietResponseDTO;
import com.example.recommendationservice.DTO.response.ExerciseRecommendationResponseDTO;
import com.example.recommendationservice.DTO.response.SleepRecommendationResponseDTO;
import com.example.recommendationservice.response.ResponseHandler;
import com.example.recommendationservice.services.IAuthenticationService;
import com.example.recommendationservice.services.IRecommendationService;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final IRecommendationService recommendationService;
    private final IAuthenticationService authenticationService;

    public RecommendationController(IRecommendationService recommendationService,
                                    IAuthenticationService authenticationService) {
        this.recommendationService = recommendationService;
        this.authenticationService = authenticationService;
    }

    // Get Sleep recommendations
    @GetMapping("/sleep")
    public ResponseEntity<Object> getSleepRecommendations(){
        long userId = authenticationService.getAuthenticatedUser();
        SleepRecommendationResponseDTO response = recommendationService.getSleepRecommendationByUserId(userId);
        return ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    // Get Exercise recommendations
    @GetMapping("/exercise")
    public ResponseEntity<Object> getExerciseRecommendations(){
        long userId = authenticationService.getAuthenticatedUser();
        ExerciseRecommendationResponseDTO response = recommendationService.getExerciseRecommendation(userId);
        return ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }

    // Get Diet recommendations
    @GetMapping("/diet")
    public ResponseEntity<Object> getDietRecommendations(){
        long userId = authenticationService.getAuthenticatedUser();
        DietResponseDTO response = recommendationService.getDietRecommendation(userId);
        return ResponseHandler.generateResponse("Fetch Data Successfully", HttpStatus.OK, response);
    }
}
