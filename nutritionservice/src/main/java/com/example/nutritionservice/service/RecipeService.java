package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.response.RecipeResponseDto;

public interface RecipeService {
    public RecipeResponseDto addRecipe(RecipeRequestDTO recipeDto);
}
