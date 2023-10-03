package com.example.nutritionservice.external;

import com.example.nutritionservice.external.enums.ActivityLevel;
import com.example.nutritionservice.external.enums.BloodGroup;
import com.example.nutritionservice.external.enums.Gender;
import com.example.nutritionservice.external.enums.GoalType;
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

    private BloodGroup bloodGroup;

    private GoalType goalType;

    private ActivityLevel activityLevel;

    private Gender gender;

    private DailySchedule dailySchedule;

    private MentalHealth mentalHealth;

    private PhysicalHealth physicalHealth;

}


