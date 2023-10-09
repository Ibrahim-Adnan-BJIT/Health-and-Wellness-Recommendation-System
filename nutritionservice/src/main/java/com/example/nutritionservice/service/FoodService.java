package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.FoodRequestDto;
import com.example.nutritionservice.dto.response.FoodRecipeResponseDto;
import com.example.nutritionservice.dto.response.FoodResponseDTO;
import com.example.nutritionservice.entity.Food;

import java.util.List;

public interface FoodService {
    public void addFood(FoodRequestDto foodDto);

    public FoodResponseDTO getFood(String foodName);

    public List<FoodRecipeResponseDto> getFoodRecipe(String foodName);
}
