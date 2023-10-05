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
public class ExerciseRecommendationResponseDTO {
    private long userId;

    private long age;

    private String goalType;

    private String gender;

    private List<RecommendedExerciseDTO> recommendedExercise;

    private List<String> additionalRecommendations;
}
