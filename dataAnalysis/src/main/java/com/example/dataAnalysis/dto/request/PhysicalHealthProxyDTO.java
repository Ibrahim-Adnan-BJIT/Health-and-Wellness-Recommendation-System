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
public class PhysicalHealthProxyDTO {
    private long age;
    private boolean smoke;
    private BloodPressure bloodPressure;
    private SleepIssue sleepIssue;
}
