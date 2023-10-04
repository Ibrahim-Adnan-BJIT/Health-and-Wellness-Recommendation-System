package com.example.progress.repository;

import com.example.progress.entity.MentalHealthFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentalHealthFeedbackRepository extends JpaRepository<MentalHealthFeedback, Integer> {
    public Optional<MentalHealthFeedback> findByUserId(long userId);
}
