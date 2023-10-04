package com.mode.main.controller;

import com.mode.main.dto.ProxyResponse;
import com.mode.main.entity.ExerciseEntity;
import com.mode.main.entity.ModeEntity;
import com.mode.main.service.ExerciseService;
import com.mode.main.service.ModeTrackingService;
import com.mode.main.sourcemodel.HealthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ModeTrackingController {
    @Autowired
    private final ModeTrackingService modeTrackingService;
    @Autowired
    private final ExerciseService exerciseService;

    public ModeTrackingController(ModeTrackingService modeTrackingService, ExerciseService exerciseService) {
        this.modeTrackingService = modeTrackingService;
        this.exerciseService = exerciseService;
    }

    @PostMapping("/v2/mode/track")
    public ProxyResponse trackMode(@RequestBody HealthDetails mode) {
        ModeEntity trackedMode = modeTrackingService.trackMode(mode);
        ExerciseEntity exercise = exerciseService.exerciseRecommendation(mode);
        return new ProxyResponse("Successfully created");
    }

    @GetMapping("/v1/mode/history/{userId}")
    public ResponseEntity<List<ModeEntity>> getUserModeHistory(@PathVariable Long userId) {
        List<ModeEntity> userModeHistory = modeTrackingService.getUserModeHistory(userId);
        return new ResponseEntity<>(userModeHistory, HttpStatus.OK);
    }

    @GetMapping("/v1/mode/exercise/{userId}")
    public ResponseEntity<ExerciseEntity> getExercises(@PathVariable Long userId) {
        ExerciseEntity exercises = exerciseService.getExercises(userId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }
}
