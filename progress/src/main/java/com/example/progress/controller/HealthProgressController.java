package com.example.progress.controller;

import com.example.progress.dto.response.HealthProgressResponseDTO;
import com.example.progress.dto.response.MentalHealthProgressResponseDTO;
import com.example.progress.dto.response.PhysicalHealthProgressResponseDTO;
import com.example.progress.external.HealthDetails;
import com.example.progress.response.ResponseHandler;
import com.example.progress.service.AuthenticationService;
import com.example.progress.service.MentalHealthService;
import com.example.progress.service.PhysicalHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HealthProgressController {
    private final PhysicalHealthService physicalHealthService;
    private final MentalHealthService mentalHealthService;
    private final AuthenticationService authenticationService;

    @PostMapping("/v2/health/track")
    public String addHealthProgress(@RequestBody HealthDetails healthDetails) {
        physicalHealthService.addPhysicalHealthProgress(healthDetails);
        mentalHealthService.addMentalHealthProgress(healthDetails);
        return "progress is tracked";
    }

    @GetMapping("/v1/health/insights")
    public ResponseEntity<?> analysisHealthProgress() {
    	long userId = authenticationService.getAuthenticatedUser();
        PhysicalHealthProgressResponseDTO physicalHealthProgressResponseDTO =
                physicalHealthService.analysisPhysicalHealth(userId);

        MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO =
                mentalHealthService.analysisMentalHealth(userId);

        HealthProgressResponseDTO healthProgressResponseDTO =
                generateHealthDTO(physicalHealthProgressResponseDTO, mentalHealthProgressResponseDTO);

        return ResponseHandler.generateResponse(new Date(), "Recent Health Progress",
                HttpStatus.OK, healthProgressResponseDTO);
    }

    private HealthProgressResponseDTO generateHealthDTO(
            PhysicalHealthProgressResponseDTO physicalHealthProgressResponseDTO,
            MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO) {
        return HealthProgressResponseDTO
                .builder()
                .userId(16)
                .physicalHealthProgressResponseDTO(physicalHealthProgressResponseDTO)
                .mentalHealthProgressResponseDTO(mentalHealthProgressResponseDTO).build();
    }
}
