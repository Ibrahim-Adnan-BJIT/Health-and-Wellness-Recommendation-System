package com.example.dataAnalysis.external;

import lombok.*;

import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailySchedule {
    private long id;

    private LocalTime wakeTime;
    private LocalTime bedTime;
    private HealthDetails healthDetails;
}
