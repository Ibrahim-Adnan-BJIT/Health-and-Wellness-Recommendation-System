package com.example.progress.repository;

import com.example.progress.entity.PhysicalHealthProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PhysicalHealthProgressRepository extends JpaRepository<PhysicalHealthProgress, Integer> {
    Optional<PhysicalHealthProgress> findByUserId(long userId);
    
    Page<PhysicalHealthProgress> findTop7ByUserIdOrderByDateDesc(long userId, Pageable pageable);
    
    @Query("SELECT php FROM PhysicalHealthProgress php " +
            "WHERE php.userId = :userId " +
            "AND php.date BETWEEN :startDate AND :endDate")
    List<PhysicalHealthProgress> findLast7DaysByUserId(
            @Param("userId") long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
