package com.example.progress.repository;

import com.example.progress.entity.MentalHealthProgress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MentalHealthProgressRepository extends JpaRepository<MentalHealthProgress, Integer> {
    Optional<MentalHealthProgress> findByUserId(long userId);

    Page<MentalHealthProgress> findTop7ByUserIdOrderByDateDesc(long userId, Pageable pageable);
    
    @Query("SELECT mhp FROM MentalHealthProgress mhp " +
            "WHERE mhp.userId = :userId " +
            "AND mhp.date BETWEEN :startDate AND :endDate")
    List<MentalHealthProgress> findLast7DaysByUserId(
            @Param("userId") long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
