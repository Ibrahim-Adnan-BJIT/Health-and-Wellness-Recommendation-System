package com.example.nutritionservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionRequestDTO {
    @NotNull(message = "Nutrition name is required")
    private String name;

    //@NotNull(message = "Provide the calories")
    private Double calories;
}
