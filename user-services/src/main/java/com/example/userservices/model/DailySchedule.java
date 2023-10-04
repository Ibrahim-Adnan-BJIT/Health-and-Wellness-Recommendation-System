package com.example.userservices.model;
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
@Entity
@Table(name = "daily_scheduled")
public class DailySchedule {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private LocalTime wakeTime; // Use LocalTime to store time
    private LocalTime bedTime; // Use LocalTime to store time

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_id", nullable = false)
    private HealthDetails healthDetails;
}
