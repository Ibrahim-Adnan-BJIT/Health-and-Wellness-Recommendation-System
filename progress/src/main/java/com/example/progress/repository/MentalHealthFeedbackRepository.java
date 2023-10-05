package com.example.progress.repository;

import com.example.progress.entity.ExerciseFeedback;
import com.example.progress.entity.MentalHealthFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MentalHealthFeedbackRepository extends JpaRepository<MentalHealthFeedback, Integer> {
    public Optional<MentalHealthFeedback> findByUserId(long userId);
}
