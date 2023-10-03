package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.RecipeDto;
import com.example.nutritionservice.dto.RecipeResponseDto;

public interface RecipeService {
    public RecipeResponseDto addRecipe(RecipeDto recipeDto);
}
