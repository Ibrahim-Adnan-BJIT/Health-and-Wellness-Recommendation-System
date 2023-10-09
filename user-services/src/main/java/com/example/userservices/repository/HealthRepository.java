package com.example.userservices.repository;

import com.example.userservices.model.HealthDetails;
import com.example.userservices.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthRepository extends JpaRepository<HealthDetails, Long> {
    Optional<HealthDetails> findByUserId(long userId);
}
