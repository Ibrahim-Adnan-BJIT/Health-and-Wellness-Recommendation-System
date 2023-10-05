package com.example.userservices.DTO.request;

import com.example.userservices.model.Enum.LifeSatisfaction;
import com.example.userservices.model.Enum.Mode;
import com.example.userservices.model.Enum.StressLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealthDTO {
    @NotNull
    private boolean depression;

    @NotNull
    private boolean anxiety;

    @NotNull
    private boolean panicDisorder;

    @NotNull
    private boolean bipolarDisorder;

    @NotNull
    private boolean schizophrenia;

    @NotEmpty(message = "Mode should not be empty")
    private String mode;  // // HAPPY, SAD, CALM, MANIC

    @NotEmpty(message = "StressLevel should not be empty")
    private String stressLevel;  // LOW, MODERATE, HIGH

    @NotEmpty(message = "LifeSatisfaction should not be empty")
    private String lifeSatisfaction; // LOW, MODERATE, HIGH
}
