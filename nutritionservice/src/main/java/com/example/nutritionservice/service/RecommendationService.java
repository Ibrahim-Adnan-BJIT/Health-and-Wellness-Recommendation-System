package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.response.RecommendationDto;
import com.example.nutritionservice.external.HealthDetails;

public interface RecommendationService {

    public void addRecommendation(HealthDetails healthDetails);
    public RecommendationDto dietRecommendation(long userId);

    public boolean isRecommendationExist(long userID, long recommendationId);
}
