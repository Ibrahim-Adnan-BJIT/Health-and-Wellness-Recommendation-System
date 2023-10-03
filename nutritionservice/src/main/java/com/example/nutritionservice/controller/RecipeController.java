package com.example.nutritionservice.controller;

import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.response.RecipeResponseDto;
import com.example.nutritionservice.response.ResponseHandler;
import com.example.nutritionservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<?> addRecipe(@RequestBody RecipeRequestDTO recipeDto) {
        RecipeResponseDto recipeResponseDto = recipeService.addRecipe(recipeDto);
        return ResponseHandler.generateResponse(new Date(), "Food nutrition added", HttpStatus.CREATED, recipeResponseDto);
    }
}
