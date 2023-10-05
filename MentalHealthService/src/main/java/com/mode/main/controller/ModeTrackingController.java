package com.mode.main.controller;

import com.mode.main.dto.ProxyResponse;
import com.mode.main.entity.ExerciseEntity;
import com.mode.main.entity.ModeEntity;
import com.mode.main.service.ExerciseService;
import com.mode.main.service.ModeTrackingService;
import com.mode.main.service.impl.AuthenticationService;
import com.mode.main.sourcemodel.HealthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ModeTrackingController {
    @Autowired
    private final ModeTrackingService modeTrackingService;
    @Autowired
    private final ExerciseService exerciseService;
    private final AuthenticationService authenticationService;

    public ModeTrackingController(ModeTrackingService modeTrackingService, ExerciseService exerciseService, AuthenticationService authenticationService) {
        this.modeTrackingService = modeTrackingService;
        this.exerciseService = exerciseService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/v2/mode/track")
    public Mono<ProxyResponse> trackMode(@RequestBody HealthDetails mode) {
        return Mono.fromRunnable(() -> {
                    ModeEntity trackedMode = modeTrackingService.trackMode(mode);
                    ExerciseEntity exercise = exerciseService.exerciseRecommendation(mode);
                })
                .thenReturn(new ProxyResponse("Successfully created"));
    }

    @GetMapping("/v1/mode/history")
    public ResponseEntity<List<ModeEntity>> getUserModeHistory() {
        long userId = authenticationService.getAuthenticatedUser();
        List<ModeEntity> userModeHistory = modeTrackingService.getUserModeHistory(userId);
        return new ResponseEntity<>(userModeHistory, HttpStatus.OK);
    }

    @GetMapping("/v1/mode/exercise")
    public ResponseEntity<ExerciseEntity> getExercises() {
        long userId = authenticationService.getAuthenticatedUser();
        ExerciseEntity exercises = exerciseService.getExercises(userId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }
}
