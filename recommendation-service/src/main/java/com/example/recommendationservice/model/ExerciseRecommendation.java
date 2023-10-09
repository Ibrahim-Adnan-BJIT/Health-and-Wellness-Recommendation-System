package com.example.recommendationservice.model;

import com.example.recommendationservice.external.Enum.Gender;
import com.example.recommendationservice.external.Enum.GoalType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise_recommendation")
public class ExerciseRecommendation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    private long userId;

    private long age;

    @Enumerated(EnumType.STRING)  // LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP
    private GoalType goalType;

    @Enumerated(EnumType.STRING)  // MALE, FEMALE
    private Gender gender;

    //@Column(name = "additionalRecommendations")
    @ElementCollection
    @CollectionTable(name = "additional_recommendations", joinColumns = @JoinColumn(name = "recommendation_id"))
    @Column(name = "additionalRecommendations")
    private List<String> additionalRecommendations;

}
