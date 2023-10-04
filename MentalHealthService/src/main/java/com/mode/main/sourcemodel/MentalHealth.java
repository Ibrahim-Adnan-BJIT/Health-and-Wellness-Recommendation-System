package com.mode.main.sourcemodel;

import com.mode.main.enums.LifeSatisfaction;
import com.mode.main.enums.Mode;
import com.mode.main.enums.StressLevel;
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

    private Mode mode;

    private StressLevel stressLevel;

    private LifeSatisfaction lifeSatisfaction;

    private HealthDetails healthDetails;
}
