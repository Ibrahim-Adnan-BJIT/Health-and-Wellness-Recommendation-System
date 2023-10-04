package com.example.recommendationservice.external;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailySchedule {
    private long id;

    private LocalTime wakeTime; // Use LocalTime to store time
    private LocalTime bedTime; // Use LocalTime to store time
}
