package com.example.progress.repository;

import com.example.progress.entity.DietFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietFeedbackRepository extends JpaRepository<DietFeedback, Integer> {
    public Optional<DietFeedback> findByUserId(long userId);
}
