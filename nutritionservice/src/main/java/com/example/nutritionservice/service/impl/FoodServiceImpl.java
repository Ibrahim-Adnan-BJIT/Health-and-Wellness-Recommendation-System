package com.example.nutritionservice.service.impl;

import com.example.nutritionservice.entity.Food;
import com.example.nutritionservice.service.FoodService;
import com.example.nutritionservice.dto.request.FoodRequestDto;
import com.example.nutritionservice.exception.CustomException;
import com.example.nutritionservice.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public void addFood(FoodRequestDto foodDto) {
        if (foodRepository.existsByName(foodDto.getName())) {
            throw new CustomException(new Date(), "food already exists", HttpStatus.BAD_REQUEST);
        }
        Food food = Food.builder().name(foodDto.getName()).build();
        foodRepository.save(food);
    }
}
