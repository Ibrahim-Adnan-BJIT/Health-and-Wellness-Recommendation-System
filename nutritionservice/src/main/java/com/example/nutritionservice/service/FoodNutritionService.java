package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.FoodNutritionRequestDTO;

public interface FoodNutritionService {

    void addFoodNutrition(long foodId, long nutritionId, FoodNutritionRequestDTO foodNutritionDto);
}
