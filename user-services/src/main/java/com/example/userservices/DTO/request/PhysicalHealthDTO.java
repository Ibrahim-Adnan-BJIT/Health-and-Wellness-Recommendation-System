package com.example.userservices.DTO.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealthDTO {
    @NotNull
    private boolean smoke;

    @NotEmpty(message = "DiabetesLevel should not be empty")
    private String diabetesLevel;  // TYPE_1, TYPE_2, NONE

    @NotEmpty(message = "BloodPressure should not be empty")
    private String bloodPressure;  // HIGH, LOW, NORMAL

    @NotEmpty(message = "MotivationLevel should not be empty")
    private String motivationLevel;  // LOW, MODERATE, HIGH

    @NotEmpty(message = "AlcoholConsumption should not be empty")
    private String alcoholConsumption;  // // NONE, OCCASIONAL, MODERATE, HEAVY

    @NotEmpty(message = "CaffeineConsumption should not be empty")
    private String caffeineConsumption;  // NONE, LOW, MODERATE, HIGH

    @NotEmpty(message = "SleepIssue should not be empty")
    private String sleepIssue;  // NONE, INSOMNIA, SNORING, SLEEP_APNEA
}
