package com.example.userservices.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentalHealthDTO {
    private boolean depression;
    private boolean anxiety;
    private boolean panicDisorder;
    private boolean bipolarDisorder;
    private boolean schizophrenia;
    private String mode;
    private String stressLevel;
    private String lifeSatisfaction;
}
