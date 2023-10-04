package com.example.recommendationservice.external;

import com.example.recommendationservice.external.Enum.LifeSatisfaction;
import com.example.recommendationservice.external.Enum.Mode;
import com.example.recommendationservice.external.Enum.StressLevel;
import com.example.recommendationservice.external.Enum.*;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealth {

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

}
