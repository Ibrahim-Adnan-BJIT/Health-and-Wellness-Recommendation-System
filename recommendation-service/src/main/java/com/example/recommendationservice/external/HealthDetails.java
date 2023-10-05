package com.example.recommendationservice.external;

import com.example.recommendationservice.external.Enum.ActivityLevel;
import com.example.recommendationservice.external.Enum.BloodGroup;
import com.example.recommendationservice.external.Enum.Gender;
import com.example.recommendationservice.external.Enum.GoalType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HealthDetails {

    private long id;

    private long userId;

    private long age;

    private Double weight;

    private Double height;

    private Double bmi;

    private Double bmr;

    @Enumerated(EnumType.STRING)  //  A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, O_NEGATIVE
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)  // LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP
    private GoalType goalType;

    @Enumerated(EnumType.STRING)  // SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)  // MALE, FEMALE
    private Gender gender;

    private DailySchedule dailySchedule; // wakeTime , sleepTime

    private MentalHealth mentalHealth;

    private PhysicalHealth physicalHealth;
}


