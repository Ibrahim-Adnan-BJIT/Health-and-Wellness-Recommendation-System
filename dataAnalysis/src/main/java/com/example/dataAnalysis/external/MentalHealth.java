package com.example.dataAnalysis.external;

import com.example.dataAnalysis.external.enums.LifeSatisfaction;
import com.example.dataAnalysis.external.enums.Mode;
import com.example.dataAnalysis.external.enums.StressLevel;
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

    private Mode mode;

    private StressLevel stressLevel;

    private LifeSatisfaction lifeSatisfaction;
}
