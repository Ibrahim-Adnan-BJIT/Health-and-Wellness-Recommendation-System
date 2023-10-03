package com.example.nutritionservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionDto {
    @NotNull(message = "Nutrition name is required")
    private String name;

    //@NotNull(message = "Provide the calories")
    private Double calories;
}
