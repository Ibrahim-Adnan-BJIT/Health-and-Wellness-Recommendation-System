package com.mode.main.sourcemodel;

import com.mode.main.enums.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealth {

    private long id;

    private boolean isSmoke;

    private DiabetesLevel diabetesLevel;

    private BloodPressure bloodPressure;

    private MotivationLevel motivationLevel;

    private AlcoholConsumption alcoholConsumption;


    private CaffeineConsumption caffeineConsumption;

    private SleepIssue sleepIssue;

    private HealthDetails healthDetails;
}