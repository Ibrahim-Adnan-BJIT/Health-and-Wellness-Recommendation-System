package com.example.groupservice.repository;

import com.example.groupservice.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepo extends JpaRepository<Interaction,Long> {
}
