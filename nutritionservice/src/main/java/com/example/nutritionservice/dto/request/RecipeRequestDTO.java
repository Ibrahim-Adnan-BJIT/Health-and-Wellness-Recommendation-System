package com.example.nutritionservice.dto;

import com.example.nutritionservice.entity.Food;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NotNull(message = "recipe must contain a process")
    private String process;

    @NotNull(message = "Food id is required")
    private Long foodId;
}
