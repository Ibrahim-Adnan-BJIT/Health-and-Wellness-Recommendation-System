package com.example.userservices.DTO.request;

import com.example.userservices.model.Enum.BloodGroup;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDetailsDTO {
    private long age;

    private Double weight;

    private Double height;

    private String bloodGroup;

    private String goalType;  // LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP

    private String activityLevel;  // SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE

    private String gender;  // MALE, FEMALE

    private DailyScheduleDTO dailyScheduleDTO;

    private MentalHealthDTO mentalHealthDTO;

    private  PhysicalHealthDTO physicalHealthDTO;
}
