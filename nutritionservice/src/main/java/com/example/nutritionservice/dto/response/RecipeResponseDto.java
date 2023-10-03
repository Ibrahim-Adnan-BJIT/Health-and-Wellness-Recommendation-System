package com.example.nutritionservice.dto;

import com.example.nutritionservice.dto.request.FoodRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDto {
    private String process;
    private FoodRequestDto foodDto;
}
