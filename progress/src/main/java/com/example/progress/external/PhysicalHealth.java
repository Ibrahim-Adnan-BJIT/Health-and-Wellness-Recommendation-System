package com.example.progress.external;

import com.example.progress.external.enums.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealth {

    private boolean smoke;

    private DiabetesLevel diabetesLevel;

    private BloodPressure bloodPressure;

    private MotivationLevel motivationLevel;

    private AlcoholConsumption alcoholConsumption;

    private CaffeineConsumption caffeineConsumption;

    private SleepIssue sleepIssue;
}
