package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.dto.response.FoodResponseDTO;
import com.example.nutritionservice.dto.response.NutritionResponseDTO;
import com.example.nutritionservice.dto.response.RecommendationDto;
import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.entity.FoodNutrition;
import com.example.nutritionservice.entity.Nutrition;
import com.example.nutritionservice.entity.Recommendation;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.external.HealthDetails;
import com.example.nutritionservice.external.enums.ActivityLevel;
import com.example.nutritionservice.external.enums.GoalType;
import com.example.nutritionservice.repository.FoodNutritionRepository;
import com.example.nutritionservice.repository.FoodRepository;
import com.example.nutritionservice.repository.RecommendationRepository;
import com.example.nutritionservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final FoodRepository foodRepository;
    private final RecommendationRepository recommendationRepository;

    private static final double CALORIES_PER_KG = 7000;

    private static final double CALORIES_PER_HOUR_PER_KG = 1.2;


    @Override
    public void addRecommendation(HealthDetails healthDetails) {
        String diabetesLevel = healthDetails.getPhysicalHealth().getDiabetesLevel().toString().toUpperCase();
        String bloodPressureLevel = healthDetails.getPhysicalHealth().getBloodPressure().toString().toUpperCase();

        double valueBasedOnActivity = getValueBasedOnActivity(healthDetails.getActivityLevel());
        double calorieIntake = calorieCalculation(valueBasedOnActivity, healthDetails.getBmr());

        double newCalorieIntake = adjustCalorieIntake(healthDetails.getGoalType(), calorieIntake);

        generateRecommendation(healthDetails, newCalorieIntake);

    }

    private void generateRecommendation(HealthDetails healthDetails, double newCalorieIntake) {
        Recommendation recommendation = recommendationRepository
                .findByUserId(healthDetails.getUserId())
                .orElse(new Recommendation());

        String suggestion = generateRecommendationMessage(healthDetails, newCalorieIntake);

        recommendation.setUserId(healthDetails.getUserId());
        recommendation.setSuggestion(suggestion);
        recommendation.setNewCalorieIntake(newCalorieIntake);
        recommendation.setBloodPressure(healthDetails.getPhysicalHealth().getBloodPressure());
        recommendationRepository.save(recommendation);
    }


    // new calorie for weight changes
    private double adjustCalorieIntake(GoalType goalType, double calorieIntake) {
        if (goalType == GoalType.LOSE_WEIGHT) {
            calorieIntake -= CALORIES_PER_KG;
        } else if (goalType == GoalType.BUILD_MUSCLE) {
            calorieIntake += CALORIES_PER_KG;
        }
        return calorieIntake;
    }

    private double getValueBasedOnActivity(ActivityLevel activityLevel) {
        double activityFactor;
        switch (activityLevel) {
            case LIGHTLY_ACTIVE:
                activityFactor = 1.275;
                break;
            case MODERATELY_ACTIVE:
                activityFactor = 1.35;
                break;
            case VERY_ACTIVE:
                activityFactor = 1.525;
                break;
            default:
                activityFactor = 1.1; // Default to sedentary
        }
        return activityFactor;
    }

    private double calorieCalculation(double valueBasedOnActivity, Double bmr) {
        return bmr * valueBasedOnActivity;
    }

    private List<Food> filterFoodsForBloodPressure(List<Food> foods, String bloodPressureLevel) {
        foods.removeIf(food -> isFoodSuitableForBloodPressure(food, bloodPressureLevel));
        return foods;
    }

    private boolean isFoodSuitableForBloodPressure(Food food, String bloodPressureLevel) {
        for (FoodNutrition foodNutrition : food.getFoodNutritionSet()) {
            Nutrition nutrition = foodNutrition.getNutrition();

            if (nutrition.getName().equalsIgnoreCase("Sodium")) {
                Double sodiumValue = foodNutrition.getQuantity();

                if (sodiumValue != null) {
                    if (sodiumValue <= 2) {
                        return true;
                    } else if (sodiumValue <= 6 && bloodPressureLevel.equals("LOW")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    private String generateRecommendationMessage(HealthDetails healthDetails, double newCalorieIntake) {

        StringBuilder message = new StringBuilder();

        if (healthDetails.getGoalType() == GoalType.LOSE_WEIGHT) {
            message.append("Your goal is to lose weight. ");
            message.append("Consider reducing your calorie intake to ")
                    .append(newCalorieIntake)
                    .append(" calories per day.");
            message.append("Focus on a balanced diet with plenty of fruits and vegetables.");
        } else if (healthDetails.getGoalType() == GoalType.BUILD_MUSCLE) {
            message.append("Your goal is to build muscle. ");
            message.append("Ensure you are getting enough protein in your diet.");
            message.append("Consider increasing your calorie intake to ")
                    .append(newCalorieIntake)
                    .append(" calories per day.");
        } else {
            message.append("Your goal is to maintain a balanced diet. ");
            message.append("Focus on a variety of foods, including fruits, vegetables, " +
                    "lean proteins, and whole grains.");
        }

        String bloodPressureLevel = healthDetails.getPhysicalHealth().getBloodPressure().toString().toUpperCase();
        if (bloodPressureLevel.equals("LOW")) {
            message.append("You have low blood pressure. Consider increasing your salt" +
                    " intake to help raise your blood pressure.");
        } else if (bloodPressureLevel.equals("HIGH")) {
            message.append("You have high blood pressure. Limit your salt intake to " +
                    "help lower your blood pressure.");
        } else {
            message.append("Your blood pressure is normal. Continue " +
                    "to maintain a healthy lifestyle with a balanced diet.");
        }
        return message.toString();
    }

    @Override
    public RecommendationDto dietRecommendation(long userId) {
        Recommendation recommendation = recommendationRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(new Date(), "no information available", HttpStatus.NOT_FOUND));

        double newCalorieIntake = recommendation.getNewCalorieIntake();
        List<Food> foods = foodRepository.findFoodsWithTotalCaloriesLessOrEqualThanCalorieIntake(newCalorieIntake);

        String bloodPressureLevel = recommendation.getBloodPressure().toString();

        if (!bloodPressureLevel.equalsIgnoreCase("NORMAL")) {
            foods = filterFoodsForBloodPressure(foods, bloodPressureLevel);
        }

        if (foods.isEmpty()) {
            recommendation.setSuggestion(recommendation.getSuggestion() + "Here are some recommended food.");
        } else {
            recommendation.setSuggestion(recommendation.getSuggestion() + "No specific food recommendations available.");
        }

        List<FoodResponseDTO> foodResponseDTOS = foods
                .stream()
                .map(this::mapToFoodDTO)
                .toList();

        return RecommendationDto.builder()
                .userId(userId).foodResponseDTOS(foodResponseDTOS)
                .suggestion(recommendation.getSuggestion())
                .build();
    }

    private FoodResponseDTO mapToFoodDTO(Food food) {
        return FoodResponseDTO
                .builder()
                .name(food.getName())
                .nutritionList(food
                        .getFoodNutritionSet()
                        .stream()
                        .map(this::mapToNutritionResponseDTO)
                        .toList())
                .build();
    }

    private NutritionResponseDTO mapToNutritionResponseDTO(FoodNutrition foodNutrition) {
        return NutritionResponseDTO
                .builder()
                .name(foodNutrition.getNutrition().getName())
                .calories(foodNutrition.getNutrition().getCalories())
                .quantityOfNutritions(foodNutrition.getQuantity())
                .build();
    }

    @Override
    public boolean isRecommendationExist(long userID, long recommendationId) {
        if (!recommendationRepository.existsByUserIdAndId(userID, recommendationId)) {
            throw new CustomException(new Date(), "failed to fetch data", HttpStatus.NOT_FOUND);
        }
        return true;
    }
}
