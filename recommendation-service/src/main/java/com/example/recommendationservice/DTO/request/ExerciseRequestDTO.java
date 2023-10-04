package com.example.recommendationservice.DTO.request;

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
public class ExerciseRequestDTO {
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Description should not be null or empty")
    private String description;

    @NotEmpty(message = "GoalType should not be null or empty")
    private String goalType;

    @PositiveOrZero(message = "MinAgeRequirement should not be a negative number or null or empty")
    private long minAgeRequirement;

    @PositiveOrZero(message = "MaxAgeRequirement should not be a negative number or null or empty")
    private long maxAgeRequirement;

    @NotEmpty(message = "DifficultyLevel should not be null or empty")
    private String difficultyLevel;

    @NotEmpty(message = "EquipmentRequired should not be null or empty")
    private String equipmentRequired;

    @Min(value = 10, message = "Duration should be at least 10")
    private long duration;

    @NotEmpty(message = "VideoTutorialLink should not be null or empty")
    private String videoTutorialLink;

    @NotEmpty(message = "SafetyPrecautions should not be null or empty")
    private String safetyPrecautions;
}
