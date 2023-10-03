package com.example.progress.repository;

import com.example.progress.entity.MentalHealthProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MentalHealthProgressRepository extends JpaRepository<MentalHealthProgress, Integer> {
    Optional<MentalHealthProgress> findByUserId(long userId);

    Page<MentalHealthProgress> findTop7ByUserIdOrderByDateDesc(long userId, Pageable pageable);
}
