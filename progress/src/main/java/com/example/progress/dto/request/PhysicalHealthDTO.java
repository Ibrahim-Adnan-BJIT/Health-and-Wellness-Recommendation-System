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
public class PhysicalHealthDTO {
    @NotNull(message = "Information required")
    private boolean smoke;

    @NotNull(message = "Diabetes information required")
    private String diabetesLevel;

    @NotNull(message = "blood pressure information required")
    private String bloodPressure;

    @NotNull(message = "motivation level information required")
    private String motivationLevel;

    @NotNull(message = "Field required")
    private String alcoholConsumption;

    @NotNull(message = "Information required")
    private String caffeineConsumption;

    @NotNull(message = "Sleep issue information required")
    private String sleepIssue;
}
