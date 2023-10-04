package com.example.recommendationservice.services;

import com.example.recommendationservice.DTO.response.DietResponseDTO;
import com.example.recommendationservice.DTO.response.ExerciseRecommendationResponseDTO;
import com.example.recommendationservice.DTO.response.SleepRecommendationResponseDTO;

public interface IRecommendationService {
    public SleepRecommendationResponseDTO getSleepRecommendationByUserId(long userId);
    public DietResponseDTO getDietRecommendation(long userId);
    public ExerciseRecommendationResponseDTO getExerciseRecommendation(long userId);
}
