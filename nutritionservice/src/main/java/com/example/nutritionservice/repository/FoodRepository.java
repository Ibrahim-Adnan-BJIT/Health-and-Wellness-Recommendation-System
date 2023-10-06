package com.example.nutritionservice.repository;

import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.entity.FoodNutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    public boolean existsByName(String name);

    public boolean existsById(Long id);

    @Query("SELECT DISTINCT f FROM Food f JOIN FETCH f.foodNutritionSet fn JOIN fn.nutrition n " +
            "GROUP BY f.id HAVING SUM(fn.quantity * n.calories) <= :maxCalories")
    List<Food> findFoodsWithTotalCaloriesLessOrEqualThanCalorieIntake(@Param("maxCalories") double maxCalories);

    Optional<Food> findByName(String name);

    List<Food> findByNameContaining(String foodName);
}
