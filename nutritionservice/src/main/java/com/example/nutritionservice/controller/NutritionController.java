package com.example.nutritionservice.controller;

import com.example.nutritionservice.service.NutritionService;
import com.example.nutritionservice.dto.request.NutritionRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nutrition")
@RequiredArgsConstructor
public class NutritionController {
    private final NutritionService nutritionService;

    @PostMapping("/add")
    public ResponseEntity<?> addNutrition(@RequestBody NutritionRequestDTO nutritionDto) {
        nutritionService.addNutrition(nutritionDto);
        return new ResponseEntity<>("Nutrition is added", HttpStatus.CREATED);
    }
}
