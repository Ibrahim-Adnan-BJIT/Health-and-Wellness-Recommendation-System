package com.example.recommendationservice.repository;

import com.example.recommendationservice.model.SleepRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SleepRecommendationRepository extends JpaRepository<SleepRecommendation, Long> {
    Optional<SleepRecommendation> findByUserId(long userId);
}
