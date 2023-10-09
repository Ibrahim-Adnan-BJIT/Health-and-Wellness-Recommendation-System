package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.entity.Nutrition;
import com.example.nutritionservice.service.NutritionService;
import com.example.nutritionservice.dto.request.NutritionRequestDTO;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.repository.NutritionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class NutritionServiceImpl implements NutritionService {
    private final NutritionRepository nutritionRepository;

    @Override
    public void addNutrition(NutritionRequestDTO nutritionDto) {
        if (nutritionRepository.existsByName(nutritionDto.getName())) {
            throw new CustomException(new Date(), "Nutrition already exists", HttpStatus.BAD_REQUEST);
        }

        double calories = (nutritionDto.getCalories() != null) ? nutritionDto.getCalories() : 0.0;

        Nutrition nutrition = Nutrition
                .builder()
                .name(nutritionDto.getName())
                .calories(calories)
                .build();
        nutritionRepository.save(nutrition);
    }
}
