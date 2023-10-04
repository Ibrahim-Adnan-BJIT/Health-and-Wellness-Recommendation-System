package com.example.recommendationservice.repository;

import com.example.recommendationservice.model.Diet;
import com.example.recommendationservice.model.ExerciseRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Optional<Diet> findByUserId(long userId);
}
