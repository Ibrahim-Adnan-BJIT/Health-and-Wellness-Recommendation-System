package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.DTO.request.ExerciseRequestDTO;
import com.example.recommendationservice.DTO.response.ExerciseResponseDTO;
import com.example.recommendationservice.exception.CustomeException;
import com.example.recommendationservice.external.Enum.DifficultyLevel;
import com.example.recommendationservice.external.Enum.GoalType;
import com.example.recommendationservice.model.Exercise;
import com.example.recommendationservice.repository.ExerciseRepository;
import com.example.recommendationservice.services.IExerciseService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExerciseService implements IExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public void createExercise(ExerciseRequestDTO requestDTO) {
        Exercise exercise = mapRequestDTOToExercise(requestDTO);
        exerciseRepository.save(exercise);
    }

    @Transactional
    public List<ExerciseResponseDTO> getAllExercises() {
        List<Exercise> exerciseEntities = exerciseRepository.findAll();
        return exerciseEntities.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Map Data to DTo
    private ExerciseResponseDTO mapToResponseDTO(Exercise exercise) {
        return ExerciseResponseDTO.builder()
                .name(exercise.getName())
                .description(exercise.getDescription())
                .goalType(exercise.getGoalType().name())
                .minAgeRequirement(exercise.getMinAgeRequirement())
                .maxAgeRequirement(exercise.getMaxAgeRequirement())
                .difficultyLevel(exercise.getDifficultyLevel().name())
                .equipmentRequired(exercise.getEquipmentRequired())
                .duration(exercise.getDuration())
                .videoTutorialLink(exercise.getVideoTutorialLink())
                .safetyPrecautions(exercise.getSafetyPrecautions())
                .build();
    }

    // Map DTO to Model
    private Exercise mapRequestDTOToExercise(ExerciseRequestDTO requestDTO) {
        return Exercise.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .goalType(parseGoalType(requestDTO.getGoalType()))
                .minAgeRequirement(requestDTO.getMinAgeRequirement())
                .maxAgeRequirement(requestDTO.getMaxAgeRequirement())
                .difficultyLevel(parseDifficultyLevel(requestDTO.getDifficultyLevel()))
                .equipmentRequired(requestDTO.getEquipmentRequired())
                .duration(requestDTO.getDuration())
                .videoTutorialLink(requestDTO.getVideoTutorialLink())
                .safetyPrecautions(requestDTO.getSafetyPrecautions())
                .build();
    }

    // Parse Difficulty Level
    private DifficultyLevel parseDifficultyLevel(String difficultyLevelValue) {
        try {
            return DifficultyLevel.valueOf(difficultyLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid DifficultyLevel." +
                    " Supported levels are EASY, MEDIUM, HARD");
        }
    }

    // Parse goal type
    private GoalType parseGoalType(String goalTypeValue) {
        try {
            return GoalType.valueOf(goalTypeValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid Goal Type." +
                    " Supported goals are LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, IMPROVE_SLEEP");
        }
    }
}
