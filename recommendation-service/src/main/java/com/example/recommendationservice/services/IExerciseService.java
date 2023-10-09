package com.example.recommendationservice.services;

import com.example.recommendationservice.DTO.request.ExerciseRequestDTO;
import com.example.recommendationservice.DTO.response.ExerciseResponseDTO;

import java.util.List;

public interface IExerciseService {
    public void createExercise(ExerciseRequestDTO requestDTO);
    public List<ExerciseResponseDTO> getAllExercises();
}
