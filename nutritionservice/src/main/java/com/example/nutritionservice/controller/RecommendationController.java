package com.example.nutritionservice.controller;

import com.example.nutritionservice.dto.response.ProxyResponse;
import com.example.nutritionservice.dto.response.RecommendationDto;
import com.example.nutritionservice.external.HealthDetails;
import com.example.nutritionservice.response.ResponseHandler;
import com.example.nutritionservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/v1/diet-recommendation/user/{userId}")
    public ResponseEntity<?> getRecommendation(@PathVariable long userId) {
        RecommendationDto recommendationDto = recommendationService.dietRecommendation(userId);
        return ResponseHandler.generateResponse(new Date(), "Dietary Recommendation",
                HttpStatus.OK, recommendationDto);
    }

    @PostMapping("/v2/diet-recommendation")
    public ProxyResponse addRecommendation(@RequestBody HealthDetails healthDetails) {
        recommendationService.addRecommendation(healthDetails);
        return new ProxyResponse("recommendation successful");
    }

    @GetMapping("/v2/check-recommendation/user/{userID}/recommendation/{recommendationId}")
    public boolean isRecommendationExist(@PathVariable long userID,
                                         @PathVariable long recommendationId) {
        return recommendationService.isRecommendationExist(userID, recommendationId);
    }


    //fetch user health detail in case recommendation missing

}
