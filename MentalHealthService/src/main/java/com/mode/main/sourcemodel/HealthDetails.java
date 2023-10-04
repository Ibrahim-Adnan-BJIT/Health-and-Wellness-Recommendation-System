package com.mode.main.sourcemodel;

import com.mode.main.enums.ActivityLevel;
import com.mode.main.enums.BloodGroup;
import com.mode.main.enums.Gender;
import com.mode.main.enums.GoalType;
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
