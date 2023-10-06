package com.example.nutritionservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nutrition_id", nullable = false)
    private Nutrition nutrition;
}
