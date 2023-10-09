package com.example.recommendationservice.DTO.request;

import jakarta.validation.constraints.*;
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


    @Min(value = 2, message = "Minimum AgeRequirement should be at least 2 and Can't be empty")
    private long minAgeRequirement;

    @Min(value = 3, message = "Maximum AgeRequirement should be at least 3 and Can't be empty")
    @Max(value = 120, message = "Maximum AgeRequirement should less than 120 and Can't be empty")
    private long maxAgeRequirement;

    @NotEmpty(message = "DifficultyLevel should not be null or empty")
    private String difficultyLevel;

    @NotEmpty(message = "EquipmentRequired should not be null or empty")
    private String equipmentRequired;

    @Min(value = 10, message = "Duration should be at least 10 and Can't be empty")
    private long duration;

    @NotEmpty(message = "VideoTutorialLink should not be null or empty")
    private String videoTutorialLink;

    @NotEmpty(message = "SafetyPrecautions should not be null or empty")
    private String safetyPrecautions;
}
