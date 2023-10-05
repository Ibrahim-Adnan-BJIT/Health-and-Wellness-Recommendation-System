package com.example.progress.repository;

import com.example.progress.entity.MentalHealthFeedback;
import com.example.progress.entity.SleepFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SleepFeedbackRepository extends JpaRepository<SleepFeedback, Integer> {
    public Optional<SleepFeedback> findByUserId(long userId);
}
