package com.example.progress.repository;

import com.example.progress.entity.DietFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DietFeedbackRepository extends JpaRepository<DietFeedback, Integer> {
    public Optional<DietFeedback> findByUserId(long userId);
}
