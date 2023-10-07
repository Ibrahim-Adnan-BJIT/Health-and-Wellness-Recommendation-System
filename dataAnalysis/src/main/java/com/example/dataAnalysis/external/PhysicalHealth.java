package com.example.dataAnalysis.external;

import com.example.dataAnalysis.external.enums.*;
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
