package com.example.groupservice.repository;

import com.example.groupservice.entity.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepo extends JpaRepository<Grouping,Long> {
         Grouping findByName(String name);
         Optional<Grouping> findById(Long id);
}
