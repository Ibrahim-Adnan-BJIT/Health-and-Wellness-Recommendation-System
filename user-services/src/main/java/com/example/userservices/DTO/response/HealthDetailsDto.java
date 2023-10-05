package com.example.userservices.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDetailsDto {
    private long userId;
    private long age;
    private Double weight;
    private Double height;
    private Double bmi;
    private Double bmr;
    private String bloodGroup;
    private String goalType;
    private String activityLevel;
    private String gender;
    private DailyScheduleDTO dailySchedule;
    private MentalHealthDTO mentalHealth;
    private PhysicalHealthDTO physicalHealth;
}
