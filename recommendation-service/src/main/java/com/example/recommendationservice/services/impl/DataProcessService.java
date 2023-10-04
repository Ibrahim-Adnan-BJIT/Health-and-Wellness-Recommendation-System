package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.services.IDataProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataProcessService implements IDataProcessService {

    private final SleepService sleepService;
    private final ExerciseRecommendationService exerciseRecommendationService;
    private final DietService dietService;

    public DataProcessService(SleepService sleepService, ExerciseRecommendationService exerciseRecommendationService, DietService dietService) {
        this.sleepService = sleepService;
        this.exerciseRecommendationService = exerciseRecommendationService;
        this.dietService = dietService;
    }

    /**
     * Send data To SleepRecommendationService, ExerciseRecommendationService, DietService for data process and calculate based
     * on multiple Algorithm and put in database
     * @Return void
     * @param healthDetails
     */
    public void importHealthDetails(HealthDetails healthDetails){
        exerciseRecommendationService.exerciseRecommendation(healthDetails);
        sleepService.sleepRecommendation(healthDetails);
        dietService.dietRecommendation(healthDetails);
    }
}
