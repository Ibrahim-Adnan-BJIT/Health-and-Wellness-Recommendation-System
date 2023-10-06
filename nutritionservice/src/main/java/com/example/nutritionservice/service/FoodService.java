package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.FoodRequestDto;
import com.example.nutritionservice.entity.Food;

public interface FoodService {
    public void addFood(FoodRequestDto foodDto);
}
