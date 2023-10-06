package com.example.nutritionservice.dto.response;

import com.example.nutritionservice.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRecipeResponseDto {
    private String name;
    private List<RecipeResponseDTO> recipeResponseDTOS;
}
