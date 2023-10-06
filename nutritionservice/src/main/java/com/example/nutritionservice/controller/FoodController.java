package com.example.nutritionservice.controller;

import com.example.nutritionservice.response.ResponseHandler;
import com.example.nutritionservice.service.FoodService;
import com.example.nutritionservice.dto.request.FoodRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/add")
    public ResponseEntity<?> addFood(@RequestBody FoodRequestDto foodDto) {
        foodService.addFood(foodDto);
        return new ResponseEntity<>("Food is added", HttpStatus.CREATED);
    }

    @GetMapping("/get/{foodName}")
    public ResponseEntity<?> getFood(@PathVariable String foodName) {
        return ResponseHandler.generateResponse(new Date(), "Food nutrition", HttpStatus.OK,
                foodService.getFood(foodName));
    }

    @GetMapping("/{foodName}/search/recipe")
    public ResponseEntity<?> getFoodRecipe(@PathVariable String foodName) {
        return ResponseHandler.generateResponse(new Date(), "Food recipe of " + foodName,
                HttpStatus.OK, foodService.getFoodRecipe(foodName));
    }
}
