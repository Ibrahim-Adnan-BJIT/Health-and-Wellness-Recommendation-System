package com.example.recommendationservice.controller;

import com.example.recommendationservice.DTO.request.ExerciseRequestDTO;
import com.example.recommendationservice.DTO.response.ExerciseResponseDTO;
import com.example.recommendationservice.response.ResponseHandler;
import com.example.recommendationservice.services.IExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    private final IExerciseService exerciseService;

    public ExerciseController(IExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    // Create Exercise
    @PostMapping("/create")
    public ResponseEntity<Object> createExercise(@Valid @RequestBody ExerciseRequestDTO requestDTO){
        exerciseService.createExercise(requestDTO);
        return ResponseHandler.generateResponse("Create exercise successfully", HttpStatus.CREATED);
    }

    // Fetch All Exercises
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllExercise(){
        List<ExerciseResponseDTO> response = exerciseService.getAllExercises();
        return ResponseHandler.generateResponse("Fetch all exercises successfully", HttpStatus.OK, response);
    }
}
