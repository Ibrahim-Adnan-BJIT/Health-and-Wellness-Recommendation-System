package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.external.Enum.AlcoholConsumption;
import com.example.recommendationservice.external.Enum.CaffeineConsumption;
import com.example.recommendationservice.external.Enum.Gender;
import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.model.Exercise;
import com.example.recommendationservice.model.ExerciseRecommendation;
import com.example.recommendationservice.repository.ExerciseRecommendationRepository;
import com.example.recommendationservice.repository.ExerciseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExerciseRecommendationService {

    private final ExerciseRecommendationRepository exerciseRecommendation;

    public ExerciseRecommendationService(ExerciseRecommendationRepository exerciseRecommendation) {
        this.exerciseRecommendation = exerciseRecommendation;
    }

    // Check if previous data is available or not
    public Optional<ExerciseRecommendation> getPreviousRecommendation(HealthDetails healthDetails) {
        return exerciseRecommendation.findByUserId(healthDetails.getUserId());
    }

    // Create or Update Recommendation
    public void exerciseRecommendation(HealthDetails healthDetails) {
        Optional<ExerciseRecommendation> previousRecommendation = getPreviousRecommendation(healthDetails);

        if (previousRecommendation.isPresent()) {
            // Update previous recommendation
            buildExerciseRecommendation(healthDetails, previousRecommendation.get());
        } else {
            // Create new recommendation
            ExerciseRecommendation recommendation = new ExerciseRecommendation();
            buildExerciseRecommendation(healthDetails, recommendation );
        }
    }

    // Process data based on recommendation
    private void buildExerciseRecommendation(HealthDetails healthDetails, ExerciseRecommendation recommendation) {

        recommendation.setUserId(healthDetails.getUserId());
        recommendation.setAge(healthDetails.getAge());
        recommendation.setGender(healthDetails.getGender());
        recommendation.setGoalType(healthDetails.getGoalType());
        recommendation.setAdditionalRecommendations(generateAdditionalRecommendations(healthDetails));

        exerciseRecommendation.save(recommendation);
    }

    // Generate List of Recommendation
    private List<String> generateAdditionalRecommendations(HealthDetails healthDetails) {
        List<String> recommendations = new ArrayList<>();

        // Recommendations based on sleep, alcohol, caffeine  issues
          recommendations.addAll(generateAlcoholRecommendations(healthDetails.getPhysicalHealth().getAlcoholConsumption()));
          recommendations.addAll(generateCaffeineRecommendations(healthDetails.getPhysicalHealth().getCaffeineConsumption()));
          recommendations.addAll(generateWeightRecommendationsBasedOnBMT(healthDetails));
          recommendations.addAll(generateWeightRecommendationsBasedOnBMR(healthDetails));

        return recommendations;
    }


    /**
     * Generates alcohol consumption recommendations based on the level of alcohol consumption.
     *
     * @param alcoholConsumption The level of alcohol consumption.
     * @return A list of recommendations.
     */
    private List<String> generateAlcoholRecommendations(AlcoholConsumption alcoholConsumption) {
        List<String> recommendations = new ArrayList<>();

        if (alcoholConsumption != null) {
            switch (alcoholConsumption) {
                case HEAVY -> {
                    recommendations.add("Limit heavy alcohol consumption, especially close to bedtime.");
                    recommendations.add("Consider reducing alcohol intake to improve Health quality.");
                }
                case MODERATE ->
                        recommendations.add("Moderate alcohol consumption is generally acceptable, but avoid excessive drinking close to bedtime.");
                case OCCASIONAL -> recommendations.add("Low alcohol consumption is less likely to interfere with sleep.");
                case NONE ->
                        recommendations.add("Avoid alcohol consumption, especially close to bedtime, for the best Health quality.");
            }
        }

        return recommendations;
    }

    /**
     * Generates caffeine consumption recommendations based on the level of caffeine consumption.
     *
     * @param caffeineConsumption The level of caffeine consumption.
     * @return A list of recommendations.
     */
    private List<String> generateCaffeineRecommendations(CaffeineConsumption caffeineConsumption) {
        List<String> recommendations = new ArrayList<>();

        if (caffeineConsumption != null) {
            switch (caffeineConsumption) {
                case HIGH -> {
                    recommendations.add("Reduce caffeine intake, especially in the afternoon and evening.");
                    recommendations.add("Avoid consuming caffeine close to bedtime to improve sleep quality.");
                }
                case MODERATE ->
                        recommendations.add("Moderate caffeine consumption is generally acceptable, but limit intake in the evening.");
                case LOW -> recommendations.add("Low caffeine consumption is less likely to interfere with sleep and Health.");
                case NONE ->
                        recommendations.add("Avoid caffeine consumption, especially in the afternoon and evening, for better Health.");
            }
        }

        return recommendations;
    }


    /**
     * Generates weight recommendations based on BMI
     *
     * @param healthDetails The level of caffeine consumption.
     * @return A list of recommendations.
     */
    private Collection<String> generateWeightRecommendationsBasedOnBMT(HealthDetails healthDetails) {
        List<String> recommendations = new ArrayList<>();

        double weight = healthDetails.getWeight();
        double height = healthDetails.getHeight();
        Gender gender = healthDetails.getGender();
        double bmi = healthDetails.getBmi();
        double bmr = healthDetails.getBmr();

        switch (gender) {
            case MALE, FEMALE -> {
                if (bmi < 18.5) {
                    recommendations.add("Your BMI is below 18.5, which is considered underweight. Consider gaining some weight.");
                } else if (bmi >= 18.5 && bmi < 24.9) {
                    recommendations.add("Your BMI is in the healthy range. Maintain your current weight for good health.");
                } else if (bmi >= 25 && bmi < 29.9) {
                    recommendations.add("Your BMI is in the overweight range. Consider losing some weight for better health.");
                } else {
                    recommendations.add("Your BMI is in the obese range. It's important to consult a healthcare professional for weight management.");
                }
            }
            default -> recommendations.add("Invalid gender specified.");
        }

        return recommendations;
    }

    /**
     * Generates weight recommendations based on BMR and CalorieIntake
     *
     * @param healthDetails The level of caffeine consumption.
     * @return A list of recommendations.
     */
    private Collection<String> generateWeightRecommendationsBasedOnBMR(HealthDetails healthDetails) {
        List<String> recommendations = new ArrayList<>();

        double weight = healthDetails.getWeight();
        double bmr = healthDetails.getBmr();

        // Provide BMR recommendation
        double calorieIntake = bmr * 1.2;
        recommendations.add("Your Basal Metabolic Rate (BMR) is approximately " + bmr + " calories per day. This represents the number of calories your body needs at rest.");

        //  Recommended based on CalorieIntake
        if (weight < calorieIntake) {
            recommendations.add("Your current weight is lower than your estimated daily calorie intake for maintenance. You may consider increasing your calorie intake for weight gain.");
        } else {
            recommendations.add("Your current weight aligns with your estimated daily calorie intake for maintenance.");
        }

        return recommendations;
    }
}
