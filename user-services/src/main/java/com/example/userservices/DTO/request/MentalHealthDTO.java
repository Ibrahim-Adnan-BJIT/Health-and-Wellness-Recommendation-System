package com.example.userservices.DTO.request;

import com.example.userservices.model.Enum.LifeSatisfaction;
import com.example.userservices.model.Enum.Mode;
import com.example.userservices.model.Enum.StressLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealthDTO {
    private boolean depression;

    private boolean anxiety;

    private boolean panicDisorder;

    private boolean bipolarDisorder;

    private boolean schizophrenia;

    private String mode;  // // HAPPY, SAD, CALM, MANIC

    private String stressLevel;  // LOW, MODERATE, HIGH

    private String lifeSatisfaction; // LOW, MODERATE, HIGH
}
