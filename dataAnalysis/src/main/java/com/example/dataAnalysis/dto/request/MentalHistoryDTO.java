package com.example.dataAnalysis.dto.request;

import com.example.dataAnalysis.external.enums.LifeSatisfaction;
import com.example.dataAnalysis.external.enums.Mode;
import com.example.dataAnalysis.external.enums.StressLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentalHistoryDTO {
    private long age;
    private boolean depression;
    private boolean anxiety;
    private boolean panicDisorder;
    private boolean bipolarDisorder;
    private boolean schizophrenia;

    private Mode mode;

    private StressLevel stressLevel;

    private LifeSatisfaction lifeSatisfaction;
}
