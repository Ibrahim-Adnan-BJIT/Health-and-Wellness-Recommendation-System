package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.request.RecipeUpdateDTO;
import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.entity.Recipe;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.repository.FoodRepository;
import com.example.nutritionservice.repository.RecipeRepository;
import com.example.nutritionservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final FoodRepository foodRepository;

    @Override
    public void addRecipe(RecipeRequestDTO recipeDto) {
        Food food = getFood(recipeDto.getFoodId());
        Recipe recipe = Recipe.builder().process(recipeDto.getProcess()).food(food).build();
        recipeRepository.save(recipe);
    }

    private Recipe getRecipe(long recipeId) {
        return recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new CustomException(new Date(), "recipe doesn't exists", HttpStatus.NOT_FOUND));
    }

    @Override
    public void updateRecipe(long recipeId, RecipeUpdateDTO recipeUpdateDTO) {
        Recipe recipe = getRecipe(recipeId);
        recipe.setProcess(recipeUpdateDTO.getProcess());
        recipeRepository.save(recipe);
    }

    private Food getFood(Long foodId) {
        return foodRepository
                .findById(foodId)
                .orElseThrow(() -> new CustomException(new Date(), "food doesn't exists", HttpStatus.NOT_FOUND));
    }
}
