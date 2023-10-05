package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.external.Enum.*;
import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.model.Diet;
import com.example.recommendationservice.repository.DietRepository;
import com.example.recommendationservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.recommendationservice.external.Enum.ActivityLevel.*;

@Service
@Slf4j
public class DietService {
    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    // Check if previous data is available or not
    public Optional<Diet> getPreviousRecommendation(HealthDetails healthDetails) {
        return dietRepository.findByUserId(healthDetails.getUserId());
    }

    // Create or Update Recommendation
    public void dietRecommendation(HealthDetails healthDetails) {
        Optional<Diet> previousRecommendation = getPreviousRecommendation(healthDetails);

        if (previousRecommendation.isPresent()) {
            // Update previous recommendation
            buildDietRecommendation(healthDetails, previousRecommendation.get());
        } else {
            // Create new recommendation
            Diet recommendation = new Diet();
            buildDietRecommendation(healthDetails, recommendation);
        }
    }

    // Process data based on recommendation
    private void buildDietRecommendation(HealthDetails healthDetails, Diet dietRecommendation) {
        dietRecommendation.setUserId(healthDetails.getUserId());
        dietRecommendation.setDailyCalorieIntake(calculateDailyCalorieIntake(healthDetails));
        dietRecommendation.setProteinIntake(calculateProteinIntake(healthDetails));
        dietRecommendation.setCarbohydrateIntake(calculateCarbohydrateIntake(healthDetails));
        dietRecommendation.setFatIntake(calculateFatIntake(healthDetails));
        dietRecommendation.setFiberIntake(calculatePreferFiberIntake(healthDetails));
        dietRecommendation.setVitaminRecommendations(calculateVitaminRecommendations(healthDetails));
        dietRecommendation.setWaterIntake(calculateWaterIntake(healthDetails));
        dietRecommendation.setRecommendedWeight(calculateRecommendedWeight(healthDetails));
        dietRecommendation.setUserBMR(healthDetails.getBmr());
        dietRecommendation.setPreferredBMR(calculatePreferredBMR(healthDetails));
        dietRecommendation.setUserBMI(healthDetails.getBmi());
        dietRecommendation.setPreferredBMI(calculatePreferredBMI(healthDetails));
        dietRecommendation.setMoreRecommendations(generateAdditionalRecommendations(healthDetails));

        // Save in Database
        dietRepository.save(dietRecommendation);
    }

    // Calculate Daily Calorie Intake
    private Double calculateDailyCalorieIntake(HealthDetails healthDetails) {
        // Get user-specific data
        GoalType goalType = healthDetails.getGoalType();
        ActivityLevel activityLevel = healthDetails.getActivityLevel();
        double bmr = healthDetails.getBmr(); // Get BMR from HealthDetails

        switch (goalType) {
            case LOSE_WEIGHT -> bmr *= 0.85; // Reduce calorie intake for weight loss
            case BUILD_MUSCLE -> bmr *= 1.15; // Increase calorie intake for muscle building
            case IMPROVE_FITNESS -> bmr *= 1.1; // Slight increase for improving fitness
            case REDUCE_STRESS -> bmr *= 0.95; // Slight reduction for stress reduction
            case IMPROVE_SLEEP -> bmr *= 1.05; // Slight increase for improving sleep
        }

        // Set daily calorie intake based on BMR and activity level
        return switch (activityLevel) {
            case SEDENTARY -> bmr * 1.2;
            case LIGHTLY_ACTIVE -> bmr * 1.375;
            case MODERATELY_ACTIVE -> bmr * 1.55;
            case VERY_ACTIVE -> bmr * 1.725;
        };
    }

    // Calculate Protection Intake
    private Double calculateProteinIntake(HealthDetails healthDetails) {
        double weight = healthDetails.getWeight();

        // Determine the activity factor based on activity level
        double activityFactor = switch (healthDetails.getActivityLevel()) {
            case SEDENTARY -> 0.8;
            case LIGHTLY_ACTIVE -> 1.0;
            case MODERATELY_ACTIVE -> 1.2;
            case VERY_ACTIVE -> 1.4;
        };

        // Calculate protein intake based on weight and activity factor
        double proteinIntake = weight * activityFactor;

        // Adjust protein intake based on gender
        if (healthDetails.getGender() == Gender.FEMALE) {
            proteinIntake *= 0.9;
        }

        return proteinIntake;
    }

    // Calculate recommended Weight
    private Double calculateRecommendedWeight(HealthDetails healthDetails) {
        // Get user-specific data
        Gender gender = healthDetails.getGender();
        Double height = healthDetails.getHeight();
        GoalType goalType = healthDetails.getGoalType();

        double targetBMI = (goalType == GoalType.LOSE_WEIGHT || goalType == GoalType.BUILD_MUSCLE) ?
                Constants.UPPER_NORMAL_BMI - Constants.MARGIN : (Constants.LOWER_NORMAL_BMI + Constants.UPPER_NORMAL_BMI) / 2.0;

        double recommendedWeight = targetBMI * (height * height);

        if (gender == Gender.FEMALE) {
            recommendedWeight *= 0.95; // Reduce recommended weight by 5% for females
        }

        // Round the recommended weight to one decimal place
        recommendedWeight = Math.round(recommendedWeight * 10.0) / 10.0;

        return recommendedWeight;
    }

    // Calculate Carbohydrate Intake
    private Double calculateCarbohydrateIntake(HealthDetails healthDetails) {
        double weight = healthDetails.getWeight();
        double height = healthDetails.getHeight();
        double age = healthDetails.getAge();
        Gender gender = healthDetails.getGender();
        GoalType goalType = healthDetails.getGoalType();

        // Constants for carbohydrate intake based on goal type (in grams per kg of body weight)
        double weightLossCarbs = 2.0; // Weight loss
        double muscleGainCarbs = 4.0; // Muscle gain
        double improveFitnessCarbs = 2.5; // Improve fitness
        double reduceStressCarbs = 3.0; // Reduce stress
        double improveSleepCarbs = 2.5; // Improve sleep

        // Calculate carbohydrate intake based on goal type and other factors
        double carbohydrateIntake = switch (goalType) {
            case LOSE_WEIGHT -> weight * weightLossCarbs;
            case BUILD_MUSCLE -> weight * muscleGainCarbs;
            case IMPROVE_FITNESS -> weight * improveFitnessCarbs;
            case REDUCE_STRESS -> weight * reduceStressCarbs;
            case IMPROVE_SLEEP -> weight * improveSleepCarbs;
        };

        // Adjust carbohydrate intake based on age, height, and gender
        carbohydrateIntake += (age / 10.0);
        carbohydrateIntake += (height / 100.0);

        // Adjust for gender (men may require more carbs than women)
        if (gender == Gender.MALE) {
            carbohydrateIntake *= 1.1; // Increase by 10% for males
        }

        return carbohydrateIntake;
    }

    // Calculate Fat Intake
    private Double calculateFatIntake(HealthDetails healthDetails) {
        double weight = healthDetails.getWeight();
        double age = healthDetails.getAge();
        Gender gender = healthDetails.getGender();
        ActivityLevel activityLevel = healthDetails.getActivityLevel();

        // Constants for fat intake based on activity level (in grams per kg of body weight)
        double sedentaryFat = 0.7;
        double lightlyActiveFat = 0.8;
        double moderatelyActiveFat = 0.9;
        double veryActiveFat = 1.0;

        // Calculate fat intake based on activity level, gender, and age
        double fatIntake = switch (activityLevel) {
            case SEDENTARY -> weight * sedentaryFat;
            case LIGHTLY_ACTIVE -> weight * lightlyActiveFat;
            case MODERATELY_ACTIVE -> weight * moderatelyActiveFat;
            case VERY_ACTIVE -> weight * veryActiveFat;
        };

        // Adjust fat intake based on age and gender
        if (gender == Gender.FEMALE) {
            // Women may require slightly less fat, so reduce it by 10%
            fatIntake *= 0.9;
        }

        // Adjust fat intake based on age (decrease by 1% for each year over 30)
        if (age > 30) {
            fatIntake -= (age - 30) * 0.01 * weight;
        }

        return fatIntake;
    }

    // Calculate Water Intake
    private Double calculateWaterIntake(HealthDetails healthDetails) {
        // Determine user's gender
        Gender gender = healthDetails.getGender();

        // Get user's age and activity level
        long age = healthDetails.getAge();
        ActivityLevel activityLevel = healthDetails.getActivityLevel();

        // Define constants for activity multipliers
        final double SEDENTARY_MULTIPLIER = 30.0;
        final double LIGHTLY_ACTIVE_MULTIPLIER = 35.0;
        final double MODERATELY_ACTIVE_MULTIPLIER = 40.0;
        final double VERY_ACTIVE_MULTIPLIER = 45.0;

        // Calculate age-based adjustment
        double ageAdjustment = (age >= 18) ? (age - 17) * 30.0 : 0.0; // Additional 30 ml for each year over 17

        // Calculate activity-based adjustment
        double activityMultiplier = switch (activityLevel) {
            case SEDENTARY -> SEDENTARY_MULTIPLIER;
            case LIGHTLY_ACTIVE -> LIGHTLY_ACTIVE_MULTIPLIER;
            case MODERATELY_ACTIVE -> MODERATELY_ACTIVE_MULTIPLIER;
            case VERY_ACTIVE -> VERY_ACTIVE_MULTIPLIER;
        };

        // Calculate total water intake recommendation
        double waterIntake = 0.0;

        if (gender == Gender.MALE) {
            waterIntake = 2500.0 + ageAdjustment + activityMultiplier;
        } else {
            waterIntake = 2000.0 + ageAdjustment + activityMultiplier;
        }

        // Round the water intake recommendation to a reasonable precision (e.g., 100 milliliters)
        waterIntake = Math.round(waterIntake / 100.0) * 100.0;

        return waterIntake;
    }

    // Calculate Preferred BMR
    private Double calculatePreferredBMR(HealthDetails healthDetails) {
        // Get user's information
        Gender gender = healthDetails.getGender();
        long age = healthDetails.getAge();
        double weightKg = healthDetails.getWeight();
        double heightCm = healthDetails.getHeight();

        // Calculate BMR using the Mifflin-St Jeor Equation
        double bmr = 0.0;

        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age);
        } else if (gender == Gender.FEMALE) {
            bmr = 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
        }

        // Round the BMR to a reasonable precision
        bmr = Math.round(bmr / 10.0) * 10.0;

        return bmr;
    }

    // Calculate Prefer Fiber
    public double calculatePreferFiberIntake(HealthDetails healthDetails) {
        double preferredFiberIntake = 0.0;
        ActivityLevel activityLevel = healthDetails.getActivityLevel();

        if (healthDetails.getAge() >= 18) {
            if (healthDetails.getGender() == Gender.MALE) {
                preferredFiberIntake = 38; // Recommended daily fiber intake for adult males (in grams).
            } else if (healthDetails.getGender() == Gender.FEMALE) {
                preferredFiberIntake = 25; // Recommended daily fiber intake for adult females (in grams).
            }
        }

        // Adjust preferred fiber intake based on activity level.
        if (activityLevel != null) {
            switch (activityLevel) {
                case SEDENTARY -> preferredFiberIntake *= 1.2;
                case LIGHTLY_ACTIVE -> preferredFiberIntake *= 1.4;
                case MODERATELY_ACTIVE -> preferredFiberIntake *= 1.6;
                case VERY_ACTIVE -> preferredFiberIntake *= 1.8;
            }
        }

        return preferredFiberIntake;
    }

    // Calculate Preferred BMT
    private double calculatePreferredBMI(HealthDetails healthDetails) {
        double bmi = healthDetails.getBmi();
        GoalType goalType = healthDetails.getGoalType();
        Gender gender = healthDetails.getGender();
        double age = healthDetails.getAge();

        // Calculate preferred BMI based on goal type, gender, age, and other factors
        double preferredBMI = bmi;

        switch (goalType) {
            case LOSE_WEIGHT -> preferredBMI -= 1.0;
            case BUILD_MUSCLE -> preferredBMI += 0.5;
            case IMPROVE_FITNESS -> preferredBMI += 0.3;
            case REDUCE_STRESS -> preferredBMI -= 0.2;
            case IMPROVE_SLEEP -> preferredBMI += 0.2;
        }

        // Adjust preferred BMI based on gender
        if (gender == Gender.MALE) {
            preferredBMI += 0.2;
        } else if (gender == Gender.FEMALE) {
            preferredBMI -= 0.2;
        }

        // Adjust preferred BMI based on age
        if (age < 30) {
            preferredBMI += 0.1;
        } else if (age < 50) {
            preferredBMI -= 0.1;
        } else {
            preferredBMI += 0.2;
        }

        return preferredBMI;
    }

    // Calculate Vitamin Recommendation
    private String calculateVitaminRecommendations(HealthDetails healthDetails) {
        StringBuilder recommendations = new StringBuilder();

        // Determine the user's age, gender, and dietary preferences
        long age = healthDetails.getAge();
        Gender gender = healthDetails.getGender();
        BloodGroup bloodGroup = healthDetails.getBloodGroup();

        // Calculate vitamin recommendations based on age and gender
        if (age >= 18) {
            // Recommendations for adults (age 18+)
            recommendations.append("For adults:");

            if (gender == Gender.MALE) {
                recommendations.append("- Consider a diet rich in vitamin A, C, and D.");
                recommendations.append("- Include sources of vitamin B12 for energy and nervous system health.");
            } else {
                recommendations.append("- Ensure adequate intake of vitamin A, C, and D.");
                recommendations.append("- Consume foods high in iron and folic acid for blood health.");
            }
        } else {
            // Recommendations for individuals under 18
            recommendations.append("For individuals under 18:");
            recommendations.append("- Focus on a balanced diet with sufficient vitamins for growth and development.");
        }

        // Additional recommendations based on blood group
        switch (bloodGroup) {
            case A_POSITIVE ->
                    recommendations.append("- Individuals with A+ blood type may benefit from a diet rich in leafy greens and lean proteins.");
            case A_NEGATIVE ->
                    recommendations.append("- Individuals with A- blood type should consider a diet similar to A+ with an emphasis on plant-based proteins.");
            case B_POSITIVE ->
                    recommendations.append("- Individuals with B+ blood type should focus on a balanced diet with diverse food groups.");
            case B_NEGATIVE ->
                    recommendations.append("- Individuals with B- blood type may benefit from a diet that includes plenty of fruits, vegetables, and lean proteins.");
            case AB_POSITIVE ->
                    recommendations.append("- Individuals with AB+ blood type can enjoy a varied diet, including a wide range of foods.");
            case AB_NEGATIVE ->
                    recommendations.append("- Individuals with AB- blood type should consider a diet similar to AB+ but may need to watch their iron intake.");
            case O_POSITIVE ->
                    recommendations.append("- Those with O+ blood type may benefit from a diet with lean proteins, fruits, and vegetables.");
            case O_NEGATIVE ->
                    recommendations.append("- Individuals with O- blood type should follow a diet similar to O+ but may need to monitor their iron levels.");
        }
        return recommendations.toString();
    }


    // Generate Recommendation
    private List<String> generateAdditionalRecommendations(HealthDetails healthDetails) {
        List<String> recommendations = new ArrayList<>();

        // Recommendations based on sleep, alcohol, caffeine, stress  issues
        recommendations.addAll(generateSleepIssueRecommendations(healthDetails.getPhysicalHealth().getSleepIssue()));
        recommendations.addAll(generateAlcoholRecommendations(healthDetails.getPhysicalHealth().getAlcoholConsumption()));
        recommendations.addAll(generateCaffeineRecommendations(healthDetails.getPhysicalHealth().getCaffeineConsumption()));
        recommendations.addAll(generateStressRecommendations(healthDetails.getMentalHealth().getStressLevel()));

        return recommendations;
    }

    /**
     * Generates sleep issue recommendations based on the type of sleep issue.
     *
     * @param sleepIssue The type of sleep issue.
     * @return A list of recommendations.
     */
    private List<String> generateSleepIssueRecommendations(SleepIssue sleepIssue) {
        List<String> recommendations = new ArrayList<>();

        if (sleepIssue != null) {
            switch (sleepIssue) {
                case INSOMNIA -> recommendations.add("Consider practicing relaxation techniques to improve sleep.");
                case SNORING -> recommendations.add("Sleep on your side to reduce snoring.");
                case SLEEP_APNEA ->
                        recommendations.add("Consult a healthcare professional for sleep apnea diagnosis and treatment.");
                default -> recommendations.add("Identify and address any underlying sleep issues.");
            }
        }

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
                    recommendations.add("Consider reducing alcohol intake to improve sleep quality.");
                }
                case MODERATE ->
                        recommendations.add("Moderate alcohol consumption is generally acceptable, but avoid excessive drinking close to bedtime.");
                case OCCASIONAL -> recommendations.add("Low alcohol consumption is less likely to interfere with sleep.");
                case NONE ->
                        recommendations.add("Avoid alcohol consumption, especially close to bedtime, for the best sleep quality.");
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
                case LOW -> recommendations.add("Low caffeine consumption is less likely to interfere with sleep.");
                case NONE ->
                        recommendations.add("Avoid caffeine consumption, especially in the afternoon and evening, for better sleep.");
            }
        }

        return recommendations;
    }

    /**
     * Generates stress management recommendations based on the level of stress.
     *
     * @param stressLevel The level of stress.
     * @return A list of recommendations.
     */
    private List<String> generateStressRecommendations(StressLevel stressLevel) {
        List<String> recommendations = new ArrayList<>();

        if (stressLevel != null) {
            switch (stressLevel) {
                case HIGH -> {
                    recommendations.add("Practice stress-reduction techniques such as meditation and deep breathing.");
                    recommendations.add("Consider regular exercise to alleviate stress and improve sleep.");
                    recommendations.add("Limit exposure to stressors, especially close to bedtime.");
                }
                case MODERATE ->
                        recommendations.add("Manage stress through relaxation exercises and a calming bedtime routine.");
                case LOW ->
                        recommendations.add("Maintain a low-stress environment and engage in relaxing activities before sleep.");
            }
        }

        return recommendations;
    }
}

