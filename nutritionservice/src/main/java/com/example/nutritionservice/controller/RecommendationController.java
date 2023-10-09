package com.example.nutritionservice.controller;

import com.example.nutritionservice.dto.response.RecommendationDto;
import com.example.nutritionservice.response.ResponseHandler;
import com.example.nutritionservice.service.AuthenticationService;
import com.example.nutritionservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final AuthenticationService authenticationService;

    @GetMapping("/food/diet/get")
    public ResponseEntity<?> getRecommendation() {
        long userId = authenticationService.getAuthenticatedUser();
        RecommendationDto recommendationDto = recommendationService.dietRecommendation(userId);
        return ResponseHandler.generateResponse(new Date(), "Dietary Recommendation",
                HttpStatus.OK, recommendationDto);
    }

}
