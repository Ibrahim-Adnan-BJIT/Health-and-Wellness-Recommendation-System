package com.example.recommendationservice.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DietResponseDTO {
    private Long userId;
    private Double dailyCalorieIntake;
    private Double proteinIntake;
    private Double carbohydrateIntake;
    private Double fatIntake;
    private Double fiberIntake;
    private String vitaminRecommendations;
    private Double waterIntake;
    private Double recommendedWeight;
    private Double userBMR;
    private Double preferredBMR;
    private Double userBMI;
    private Double preferredBMI;
    private List<String> moreRecommendations;
}
