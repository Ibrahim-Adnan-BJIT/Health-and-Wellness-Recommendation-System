package com.mode.main.repository;

import com.mode.main.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
     public Optional<ExerciseEntity> findByUserId(Long userId);
}
