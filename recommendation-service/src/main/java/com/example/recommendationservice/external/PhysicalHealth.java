package com.example.recommendationservice.external;

import com.example.recommendationservice.external.Enum.*;
import com.example.recommendationservice.external.Enum.*;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealth {
    private long id;

    private boolean smoke;

    @Enumerated(EnumType.STRING)  // TYPE_1, TYPE_2, NONE
    private DiabetesLevel diabetesLevel;

    @Enumerated(EnumType.STRING)  // HIGH, LOW, NORMAL
    private BloodPressure bloodPressure;

    @Enumerated(EnumType.STRING)  // LOW, MODERATE, HIGH
    private MotivationLevel motivationLevel;

    @Enumerated(EnumType.STRING)  // NONE, OCCASIONAL, MODERATE, HEAVY
    private AlcoholConsumption alcoholConsumption;

    @Enumerated(EnumType.STRING)  // NONE, LOW, MODERATE, HIGH
    private CaffeineConsumption caffeineConsumption;

    @Enumerated(EnumType.STRING)  // NONE, INSOMNIA, SNORING, SLEEP_APNEA
    private SleepIssue sleepIssue;

}
