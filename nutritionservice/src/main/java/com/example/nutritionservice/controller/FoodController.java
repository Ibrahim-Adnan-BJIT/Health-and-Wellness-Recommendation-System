package com.example.nutritionservice.controller;

import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.service.FoodService;
import com.example.nutritionservice.dto.request.FoodRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
