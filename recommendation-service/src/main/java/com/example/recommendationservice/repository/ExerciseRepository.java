package com.example.recommendationservice.repository;

import com.example.recommendationservice.external.Enum.GoalType;
import com.example.recommendationservice.model.Exercise;
import com.example.recommendationservice.model.SleepRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByGoalTypeAndMinAgeRequirementLessThanEqualAndMaxAgeRequirementGreaterThanEqual(
            GoalType goalType, long minAgeRequirement, long maxAgeRequirement);
}
