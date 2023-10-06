package com.example.nutritionservice.dto.response;

import com.example.nutritionservice.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDto {
    private long userId;
    private String suggestion;
    private List<FoodResponseDTO> foodResponseDTOS;
}
