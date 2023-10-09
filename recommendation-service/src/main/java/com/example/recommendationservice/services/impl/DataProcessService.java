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
     * This method is responsible for sending health data to the SleepRecommendationService, ExerciseRecommendationService, and DietService
     * for data processing. It calculates recommendations based on multiple algorithms and stores the results in the database.
     *
     * @param healthDetails An object containing health-related information to be processed.
     * @return void
     */
    public void importHealthDetails(HealthDetails healthDetails){
        // Calculate exercise recommendations based on healthDetails
        exerciseRecommendationService.exerciseRecommendation(healthDetails);

        // Calculate sleep recommendations based on healthDetails
        sleepService.sleepRecommendation(healthDetails);

        // Calculate diet recommendations based on healthDetails
        dietService.dietRecommendation(healthDetails);
    }

}
