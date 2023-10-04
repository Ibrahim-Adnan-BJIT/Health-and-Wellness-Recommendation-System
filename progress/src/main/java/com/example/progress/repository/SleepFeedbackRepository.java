package com.example.progress.repository;

import com.example.progress.entity.SleepFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SleepFeedbackRepository extends JpaRepository<SleepFeedback, Integer> {
    public Optional<SleepFeedback> findByUserId(long userId);
}
