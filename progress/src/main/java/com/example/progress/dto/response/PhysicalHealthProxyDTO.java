package com.example.progress.dto.response;

import com.example.progress.external.enums.*;
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
