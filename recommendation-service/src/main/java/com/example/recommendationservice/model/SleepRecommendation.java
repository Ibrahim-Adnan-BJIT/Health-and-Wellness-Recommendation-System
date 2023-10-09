package com.example.recommendationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sleep_recommendation")
public class SleepRecommendation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private long userId;

    @Column(name = "recommendedSleepHours")
    private long recommendedSleepHours;

    @Column(name = "sleepDuration")
    private int sleepDuration;

    @Column(name = "bedtime")
    private String bedtime;

    @Column(name = "wakeTime")
    private String wakeTime;

    @Column(name = "qualityOfSleep")
    private int qualityOfSleep;

    @Column(name = "recommendedSleepPosition")
    private String recommendedSleepPosition;

    @Column(name = "nappingRecommendation")
    private String nappingRecommendation;

    @Column(name = "needToTakeMedicine")
    private boolean needToTakeMedicine;

    @Column(name = "needToConsultDoctor")
    private boolean needToConsultDoctor;

    @Column(name = "additionalRecommendations")
    private List<String> additionalRecommendations;
}
