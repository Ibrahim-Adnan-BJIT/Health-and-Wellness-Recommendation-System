package com.example.nutritionservice.external;

import com.example.nutritionservice.external.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealth {
    private boolean depression;
    private boolean anxiety;
    private boolean panicDisorder;
    private boolean bipolarDisorder;
    private boolean schizophrenia;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    @Enumerated(EnumType.STRING)
    private StressLevel stressLevel;

    @Enumerated(EnumType.STRING)
    private LifeSatisfaction lifeSatisfaction;

    @OneToOne(fetch = FetchType.LAZY)
    private HealthDetails healthDetails;
}
