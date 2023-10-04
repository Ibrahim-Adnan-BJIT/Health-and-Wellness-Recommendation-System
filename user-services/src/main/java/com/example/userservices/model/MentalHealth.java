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
@Table(name = "mental_health")
public class MentalHealth {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private boolean depression;
    private boolean anxiety;
    private boolean panicDisorder;
    private boolean bipolarDisorder;
    private boolean schizophrenia;

    @Enumerated(EnumType.STRING)  // HAPPY, SAD, CALM, MANIC
    private Mode mode;

    @Enumerated(EnumType.STRING)  // LOW, MODERATE, HIGH
    private StressLevel stressLevel;

    @Enumerated(EnumType.STRING)
    private LifeSatisfaction lifeSatisfaction; // LOW, MODERATE, HIGH

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_id", nullable = false)
    private HealthDetails healthDetails;
}
