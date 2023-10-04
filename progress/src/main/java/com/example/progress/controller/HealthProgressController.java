package com.example.progress.controller;

import com.example.progress.dto.response.HealthProgressResponseDTO;
import com.example.progress.dto.response.MentalHealthProgressResponseDTO;
import com.example.progress.dto.response.PhysicalHealthProgressResponseDTO;
import com.example.progress.dto.response.ProxyResponse;
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
@RequestMapping("/api/v1/health")
public class HealthProgressController {
    private final PhysicalHealthService physicalHealthService;
    private final MentalHealthService mentalHealthService;
    private final AuthenticationService authenticationService;

    @GetMapping("/insights")
    public ResponseEntity<?> analysisHealthProgress() {
        long userId = authenticationService.getAuthenticatedUser();
        PhysicalHealthProgressResponseDTO physicalHealthProgressResponseDTO =
                physicalHealthService.analysisPhysicalHealth(userId);

        MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO =
                mentalHealthService.analysisMentalHealth(userId);

        HealthProgressResponseDTO healthProgressResponseDTO =
                generateHealthDTO(physicalHealthProgressResponseDTO, mentalHealthProgressResponseDTO, userId);

        return ResponseHandler.generateResponse(new Date(), "Recent Health Progress",
                HttpStatus.OK, healthProgressResponseDTO);
    }

    private HealthProgressResponseDTO generateHealthDTO(
            PhysicalHealthProgressResponseDTO physicalHealthProgressResponseDTO,
            MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO,
            long userId) {
        return HealthProgressResponseDTO
                .builder()
                .userId(userId)
                .physicalHealthProgressResponseDTO(physicalHealthProgressResponseDTO)
                .mentalHealthProgressResponseDTO(mentalHealthProgressResponseDTO)
                .build();
    }
}
