package com.example.userservices.DTO.request;

import com.example.userservices.model.Enum.BloodGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDetailsDTO {
    @NotNull
    @Min(value = 0, message = "Age should not be negative or empty or null")
    private long age;

    @NotNull
    @Min(value = 0, message = "Weight should not be negative or empty or null")
    private Double weight;

    @NotNull
    @Min(value = 0, message = "Height should not be negative or empty or null")
    private Double height;

    @NotEmpty(message = "BloodGroup should not be empty or null")
    private String bloodGroup;

    @NotEmpty(message = "GoalType should not be empty or null")
    private String goalType;  // LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP

    @NotEmpty(message = "ActivityLevel should not be empty")
    private String activityLevel;  // SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE

    @NotEmpty(message = "Gender should not be empty")
    private String gender;  // MALE, FEMALE

    @Valid
    private DailyScheduleDTO dailyScheduleDTO;

    @Valid
    private MentalHealthDTO mentalHealthDTO;

    @Valid
    private  PhysicalHealthDTO physicalHealthDTO;
}
