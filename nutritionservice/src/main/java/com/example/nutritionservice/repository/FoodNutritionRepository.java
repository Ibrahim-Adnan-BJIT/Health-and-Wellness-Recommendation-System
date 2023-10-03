package com.example.nutritionservice.repository;

import com.example.nutritionservice.entity.FoodNutrition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodNutritionRepository extends JpaRepository<FoodNutrition, Integer> {
}
