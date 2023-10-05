package com.example.progress.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealthDTO {
    @NotNull(message = "depression field required")
    private boolean depression;

    @NotNull(message = "Anxiety field required")
    private boolean anxiety;

    @NotNull(message = "Panic disorder field required")
    private boolean panicDisorder;

    @NotNull(message = "Bipolar disorder field required")
    private boolean bipolarDisorder;

    @NotNull(message = "Schizophrenia field required")
    private boolean schizophrenia;

    @NotNull(message = "Mode field required")
    private String mode;

    @NotNull(message = "Stress level field required")
    private String stressLevel;

    @NotNull(message = "life satisfaction field required")
    private String lifeSatisfaction;
}
