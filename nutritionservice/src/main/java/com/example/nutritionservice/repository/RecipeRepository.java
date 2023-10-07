package com.example.nutritionservice.repository;

import com.example.nutritionservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
