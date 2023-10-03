package com.example.progress.repository;

import com.example.progress.entity.PhysicalHealthProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhysicalHealthProgressRepository extends JpaRepository<PhysicalHealthProgress, Integer> {
    Optional<PhysicalHealthProgress> findByUserId(long userId);
    
    Page<PhysicalHealthProgress> findTop7ByUserIdOrderByDateDesc(long userId, Pageable pageable);
}
