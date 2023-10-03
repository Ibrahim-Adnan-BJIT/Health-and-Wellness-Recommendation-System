package com.example.nutritionservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "food")
    private List<FoodNutrition> foodNutritionSet = new ArrayList<>();

    @OneToMany(mappedBy = "food", orphanRemoval = true)
    private List<Recipe> recipeSet = new ArrayList<>();

    @ManyToMany(mappedBy = "foods")
    private List<Recommendation> recommendations;
}
