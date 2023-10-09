package com.example.nutritionservice.entity;

import com.example.nutritionservice.entity.FoodNutrition;
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
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Column(name = "calories(g)")
    private Double calories;

    @OneToMany(mappedBy = "nutrition")
    private List<FoodNutrition> foodNutritionSet = new ArrayList<>();
}
