package com.example.userservices.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalHealthDTO {
    private boolean smoke;
    private String diabetesLevel;
    private String bloodPressure;
    private String motivationLevel;
    private String alcoholConsumption;
    private String caffeineConsumption;
    private String sleepIssue;
}
