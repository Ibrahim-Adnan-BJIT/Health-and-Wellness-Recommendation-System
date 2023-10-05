package com.example.progress.repository;

import com.example.progress.entity.ExerciseFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExerciseFeedbackRepository extends JpaRepository<ExerciseFeedback, Integer> {
    public Optional<ExerciseFeedback> findByUserId(long userId);
}
