package com.example.progress.external;

import com.example.progress.external.enums.LifeSatisfaction;
import com.example.progress.external.enums.Mode;
import com.example.progress.external.enums.StressLevel;
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
