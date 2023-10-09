package com.example.recommendationservice.model;

import com.example.recommendationservice.external.Enum.DifficultyLevel;
import com.example.recommendationservice.external.Enum.GoalType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalType goalType;

    @Column(name = "min_age_requirement", nullable = false)
    private long minAgeRequirement;

    @Column(name = "max_age_requirement", nullable = false)
    private long maxAgeRequirement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel difficultyLevel;

    @Column(name = "equipment_required", nullable = false)
    private String equipmentRequired;

    @Column(nullable = false)
    private long duration;

    @Column(name = "video_tutorial_link")
    private String videoTutorialLink;

    @Column(name = "safety_precautions")
    private String safetyPrecautions;
}
