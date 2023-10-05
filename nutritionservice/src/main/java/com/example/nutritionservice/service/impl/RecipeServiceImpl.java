package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.dto.request.FoodRequestDto;
import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.response.RecipeResponseDto;
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
    public RecipeResponseDto addRecipe(RecipeRequestDTO recipeDto) {
        Food food = getFood(recipeDto.getFoodId());
        Recipe recipe = Recipe.builder().process(recipeDto.getProcess()).food(food).build();
        Recipe recipe1 = recipeRepository.save(recipe);
        return RecipeResponseDto.builder().process(recipe1.getProcess())
                .foodDto(new FoodRequestDto(recipe1.getFood().getName())).build();
    }

    private Food getFood(Long foodId) {
        return foodRepository
                .findById(foodId)
                .orElseThrow(() -> new CustomException(new Date(), "food doesn't exists", HttpStatus.NOT_FOUND));
    }
}
