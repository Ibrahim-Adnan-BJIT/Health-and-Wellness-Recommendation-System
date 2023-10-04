package com.example.progress.repository;

import com.example.progress.entity.ExerciseFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseFeedbackRepository extends JpaRepository<ExerciseFeedback, Integer> {
    public Optional<ExerciseFeedback> findByUserId(long userId);
}
