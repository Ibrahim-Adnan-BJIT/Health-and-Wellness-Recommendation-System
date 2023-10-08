package com.example.nutritionservice.service;

import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.request.RecipeUpdateDTO;

public interface RecipeService {
    public void addRecipe(RecipeRequestDTO recipeDto);

    void updateRecipe(long recipeId, RecipeUpdateDTO recipeUpdateDTO);
}
