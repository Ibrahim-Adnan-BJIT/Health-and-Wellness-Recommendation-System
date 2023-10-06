package com.example.dataAnalysis.dto.request;

import com.example.dataAnalysis.external.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalHealthHistoryDTO {
    private long age;
    private boolean smoke;
    private DiabetesLevel diabetesLevel;
    private BloodPressure bloodPressure;
    private MotivationLevel motivationLevel;
    private AlcoholConsumption alcoholConsumption;
    private CaffeineConsumption caffeineConsumption;
    private SleepIssue sleepIssue;
}
