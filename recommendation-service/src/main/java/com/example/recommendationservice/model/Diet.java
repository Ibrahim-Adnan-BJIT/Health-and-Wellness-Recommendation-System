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
@Table(name = "diet_recommendation")
public class Diet {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private long userId;

    @Column(name = "daily_calorie_intake")
    private Double dailyCalorieIntake;

    @Column(name = "protein_intake")
    private Double proteinIntake;

    @Column(name = "carbohydrate_intake")
    private Double carbohydrateIntake;

    @Column(name = "fat_intake")
    private Double fatIntake;

    @Column(name = "fiber_intake")
    private Double fiberIntake;

    @Column(name = "vitamin_recommendations", length = 1000)
    private String vitaminRecommendations;

    @Column(name = "water_intake")
    private Double waterIntake;

    @Column(name = "recommended_weight")
    private Double recommendedWeight;

    @Column(name = "user_bmr")
    private Double userBMR;

    @Column(name = "preferred_bmr")
    private Double preferredBMR;

    @Column(name = "user_bmi")
    private Double userBMI;

    @Column(name = "preferred_bmi")
    private Double preferredBMI;

    @ElementCollection
    @CollectionTable(name = "more_recommendations", joinColumns = @JoinColumn(name = "recommendation_id"))
    @Column(name = "moreRecommendations")
    private List<String> moreRecommendations;

}
