package com.example.recommendationservice.DTO.response;

import com.example.recommendationservice.external.Enum.DifficultyLevel;
import com.example.recommendationservice.external.Enum.GoalType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedExerciseDTO {

    private String name;

    private String description;

    private GoalType goalType;

    private long minAgeRequirement;

    private long maxAgeRequirement;

    private DifficultyLevel difficultyLevel;

    private String equipmentRequired;

    private long duration;

    private String videoTutorialLink;

    private String safetyPrecautions;
}
