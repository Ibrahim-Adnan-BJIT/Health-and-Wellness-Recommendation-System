package com.example.recommendationservice.DTO.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseResponseDTO {

    private String name;

    private String description;

    private String goalType;

    private long minAgeRequirement;

    private long maxAgeRequirement;

    private String difficultyLevel;

    private String equipmentRequired;

    private long duration;

    private String videoTutorialLink;

    private String safetyPrecautions;
}
