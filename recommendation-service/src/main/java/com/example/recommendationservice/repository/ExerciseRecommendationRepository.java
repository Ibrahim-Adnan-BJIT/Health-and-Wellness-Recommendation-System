package com.example.recommendationservice.repository;

import com.example.recommendationservice.model.ExerciseRecommendation;
import com.example.recommendationservice.model.SleepRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRecommendationRepository extends JpaRepository<ExerciseRecommendation, Long> {
    Optional<ExerciseRecommendation> findByUserId(long userId);
}
