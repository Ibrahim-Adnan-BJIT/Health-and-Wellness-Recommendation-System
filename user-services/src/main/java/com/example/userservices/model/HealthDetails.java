package com.example.userservices.model;

import com.example.userservices.model.Enum.ActivityLevel;
import com.example.userservices.model.Enum.BloodGroup;
import com.example.userservices.model.Enum.Gender;
import com.example.userservices.model.Enum.GoalType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "health_details")
public class HealthDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "age", nullable = false)
    private long age;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "bmi", nullable = false)
    private Double bmi;

    @Column(name = "bmr", nullable = false)
    private Double bmr;

    @Enumerated(EnumType.STRING)  //  A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, O_NEGATIVE
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)  // LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP
    private GoalType goalType;

    @Enumerated(EnumType.STRING)  // SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)  // MALE, FEMALE
    private Gender gender;

    @OneToOne(mappedBy = "healthDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private DailySchedule dailySchedule; // wakeTime , sleepTime

    @OneToOne(mappedBy = "healthDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private MentalHealth mentalHealth;

    @OneToOne(mappedBy = "healthDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private PhysicalHealth physicalHealth;
}


