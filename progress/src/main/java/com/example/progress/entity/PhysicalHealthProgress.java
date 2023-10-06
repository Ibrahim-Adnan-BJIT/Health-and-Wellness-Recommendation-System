package com.example.progress.entity;

import com.example.progress.external.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalHealthProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long userId;

    private long age;

    private boolean smoke;

    @Enumerated(EnumType.STRING)
    private DiabetesLevel diabetesLevel;

    @Enumerated(EnumType.STRING)
    private BloodPressure bloodPressure;

    @Enumerated(EnumType.STRING)
    private MotivationLevel motivationLevel;

    @Enumerated(EnumType.STRING)
    private AlcoholConsumption alcoholConsumption;

    @Enumerated(EnumType.STRING)
    private CaffeineConsumption caffeineConsumption;

    @Enumerated(EnumType.STRING)
    private SleepIssue sleepIssue;

    private LocalDate date;
}
