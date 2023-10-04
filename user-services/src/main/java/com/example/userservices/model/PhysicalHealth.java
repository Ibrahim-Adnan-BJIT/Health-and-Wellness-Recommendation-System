package com.example.userservices.model;

import com.example.userservices.model.Enum.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "physical_health")
public class PhysicalHealth {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private boolean smoke;

    @Enumerated(EnumType.STRING)  // TYPE_1, TYPE_2, NONE
    private DiabetesLevel diabetesLevel;

    @Enumerated(EnumType.STRING)  // HIGH, LOW, NORMAL
    private BloodPressure bloodPressure;

    @Enumerated(EnumType.STRING)  // LOW, MODERATE, HIGH
    private MotivationLevel motivationLevel;

    @Enumerated(EnumType.STRING)  // NONE, OCCASIONAL, MODERATE, HEAVY
    private AlcoholConsumption alcoholConsumption;

    @Enumerated(EnumType.STRING)  // NONE, LOW, MODERATE, HIGH
    private CaffeineConsumption caffeineConsumption;

    @Enumerated(EnumType.STRING)  // NONE, INSOMNIA, SNORING, SLEEP_APNEA
    private SleepIssue sleepIssue;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_id", nullable = false)
    private HealthDetails healthDetails;
}
