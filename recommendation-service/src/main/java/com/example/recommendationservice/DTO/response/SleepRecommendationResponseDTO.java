package com.example.recommendationservice.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SleepRecommendationResponseDTO {
    private long userId;
    private long recommendedSleepHours;
    private int sleepDuration;
    private String bedtime;
    private String wakeTime;
    private int qualityOfSleep;
    private String recommendedSleepPosition;
    private String nappingRecommendation;
    private boolean needToTakeMedicine;
    private boolean needToConsultDoctor;
    private List<String> additionalRecommendations;
}
