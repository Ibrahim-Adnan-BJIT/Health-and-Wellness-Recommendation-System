package com.example.nutritionservice.repository;

import com.example.nutritionservice.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<Nutrition, Long> {
    public boolean existsByName(String name);
}
