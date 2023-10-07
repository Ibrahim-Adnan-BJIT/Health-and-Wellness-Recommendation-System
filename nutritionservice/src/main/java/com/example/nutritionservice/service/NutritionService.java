package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.NutritionRequestDTO;

public interface NutritionService {
    public void addNutrition(NutritionRequestDTO nutritionDto);
}
