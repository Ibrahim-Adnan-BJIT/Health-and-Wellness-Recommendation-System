package com.example.nutritionservice.repository;

import com.example.nutritionservice.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    Optional<Recommendation> findByUserId(long userId);

    public boolean existsByUserIdAndId(long userId, Long recommendationId);
}
