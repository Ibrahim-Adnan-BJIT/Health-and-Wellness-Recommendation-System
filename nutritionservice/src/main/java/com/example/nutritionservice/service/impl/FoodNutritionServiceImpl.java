package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.entity.FoodNutrition;
import com.example.nutritionservice.service.FoodNutritionService;
import com.example.nutritionservice.entity.Nutrition;
import com.example.nutritionservice.dto.request.FoodNutritionRequestDTO;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.repository.FoodNutritionRepository;
import com.example.nutritionservice.repository.FoodRepository;
import com.example.nutritionservice.repository.NutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FoodNutritionServiceImpl implements FoodNutritionService {
    private final FoodNutritionRepository foodNutritionRepository;
    private final FoodRepository foodRepository;
    private final NutritionRepository nutritionRepository;

    @Override
    public void addFoodNutrition(long foodId,
                                 long nutritionId,
                                 FoodNutritionRequestDTO foodNutritionDto) {
        Food food = getFood(foodId);
        Nutrition nutrition = getNutrition(nutritionId);

        if (foodNutritionRepository.existsByFood_IdAndNutrition_Id(foodId, nutritionId)) {
            throw new CustomException(new Date(), "food nutrition already exists", HttpStatus.BAD_REQUEST);
        }

        FoodNutrition foodNutrition = FoodNutrition
                .builder()
                .quantity(foodNutritionDto.getQuantity())
                .food(food)
                .nutrition(nutrition)
                .build();
        foodNutritionRepository.save(foodNutrition);
    }

    private Nutrition getNutrition(long nutritionId) {
        return nutritionRepository.findById(nutritionId)
                .orElseThrow(() -> new CustomException(new Date(), "nutrition not exists", HttpStatus.NOT_FOUND));
    }

    private Food getFood(long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new CustomException(new Date(), "food not exists", HttpStatus.NOT_FOUND));
    }
}
