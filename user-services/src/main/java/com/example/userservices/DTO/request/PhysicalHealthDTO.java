package com.example.userservices.DTO.request;

import com.example.userservices.model.Enum.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealthDTO {
    private boolean smoke;

    private String diabetesLevel;  // TYPE_1, TYPE_2, NONE

    private String bloodPressure;  // HIGH, LOW, NORMAL

    private String motivationLevel;  // LOW, MODERATE, HIGH

    private String alcoholConsumption;  // // NONE, OCCASIONAL, MODERATE, HEAVY

    private String caffeineConsumption;  // NONE, LOW, MODERATE, HIGH

    private String sleepIssue;  // NONE, INSOMNIA, SNORING, SLEEP_APNEA
}
