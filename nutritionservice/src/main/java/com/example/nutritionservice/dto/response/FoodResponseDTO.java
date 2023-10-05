package com.example.nutritionservice.dto.response;

import com.example.nutritionservice.entity.FoodNutrition;
import com.example.nutritionservice.entity.Nutrition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDTO {
    private String name;
    private List<NutritionResponseDTO> nutritionList;
}
