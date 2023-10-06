package com.example.progress.dto.response;

import com.example.progress.external.enums.LifeSatisfaction;
import com.example.progress.external.enums.Mode;
import com.example.progress.external.enums.StressLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentalHealthProxyDTO {
    private long age;
    private boolean depression;
    private boolean anxiety;
    private StressLevel stressLevel;
}
