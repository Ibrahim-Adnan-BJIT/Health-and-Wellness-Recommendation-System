package com.example.nutritionservice.controller;

import com.example.nutritionservice.service.FoodNutritionService;
import com.example.nutritionservice.dto.request.FoodNutritionRequestDTO;
import com.example.nutritionservice.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodNutritionController {
    private final FoodNutritionService foodNutritionService;

    @PostMapping("/{foodId}/nutrition/{nutritionId}/add")
    public ResponseEntity<?> addFoodNutrition(@PathVariable long foodId,
                                              @PathVariable long nutritionId,
                                              @RequestBody FoodNutritionRequestDTO foodNutritionDto) {
        foodNutritionService.addFoodNutrition(foodId, nutritionId, foodNutritionDto);
        return ResponseHandler.generateResponse(new Date(), "Food nutrition added", HttpStatus.CREATED);
    }
}
