package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.dto.response.FoodRecipeResponseDto;
import com.example.nutritionservice.dto.response.FoodResponseDTO;
import com.example.nutritionservice.dto.response.NutritionResponseDTO;
import com.example.nutritionservice.dto.response.RecipeResponseDTO;
import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.entity.FoodNutrition;
import com.example.nutritionservice.entity.Recipe;
import com.example.nutritionservice.service.FoodService;
import com.example.nutritionservice.dto.request.FoodRequestDto;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public void addFood(FoodRequestDto foodDto) {
        if (foodRepository.existsByName(foodDto.getName())) {
            throw new CustomException(new Date(), "food already exists", HttpStatus.BAD_REQUEST);
        }
        Food food = Food.builder().name(foodDto.getName()).build();
        foodRepository.save(food);
    }

    @Override
    public FoodResponseDTO getFood(String foodName) {
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new CustomException(new Date(), "food information not found", HttpStatus.NOT_FOUND));

        return FoodResponseDTO.builder()
                .name(food.getName())
                .nutritionList(
                        food
                                .getFoodNutritionSet()
                                .stream()
                                .map(this::mapToNutritionResponseDTO)
                                .toList()
                )
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
    public List<FoodRecipeResponseDto> getFoodRecipe(String foodName) {
        List<Food> foods = foodRepository.findByNameContaining(foodName);
        if (foods.isEmpty()) {
            throw new CustomException(new Date(), "Recipe not found", HttpStatus.NOT_FOUND);
        }
        return foods.stream().map(food -> FoodRecipeResponseDto.builder()
                        .name(food.getName())
                        .recipeResponseDTOS(
                                food
                                        .getRecipeSet()
                                        .stream()
                                        .map(this::mapToRecipe)
                                        .toList()
                        )
                        .build())
                .toList();
    }

    private RecipeResponseDTO mapToRecipe(Recipe recipe) {
        return RecipeResponseDTO.builder().process(recipe.getProcess()).build();
    }
}
