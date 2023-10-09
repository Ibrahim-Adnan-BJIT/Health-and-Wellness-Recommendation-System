package com.example.nutritionservice.controller;

import com.example.nutritionservice.dto.request.RecipeRequestDTO;
import com.example.nutritionservice.dto.request.RecipeUpdateDTO;
import com.example.nutritionservice.response.ResponseHandler;
import com.example.nutritionservice.service.RecipeService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<?> addRecipe(@RequestBody RecipeRequestDTO recipeDto) {
        recipeService.addRecipe(recipeDto);
        return ResponseHandler.generateResponse(new Date(), "Food recipe added", HttpStatus.CREATED);
    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<?> updateRecipe(@PathVariable long recipeId,
                                          @RequestBody RecipeUpdateDTO recipeUpdateDTO) {
        recipeService.updateRecipe(recipeId, recipeUpdateDTO);
        return ResponseHandler.generateResponse(new Date(), "Food recipe updated", HttpStatus.OK);
    }
}
