package com.example.nutritionservice.entity;

import com.example.nutritionservice.external.enums.BloodPressure;
import com.example.nutritionservice.external.enums.GoalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;

    private double newCalorieIntake;

    @Enumerated(EnumType.STRING)
    private BloodPressure bloodPressure;

    @Column(columnDefinition = "TEXT")
    private String suggestion;

}
