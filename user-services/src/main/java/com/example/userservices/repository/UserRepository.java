package com.example.userservices.repository;

import com.example.userservices.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(long userId);
}
