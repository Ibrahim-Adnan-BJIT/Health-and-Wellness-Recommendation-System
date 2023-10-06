package com.example.progress.controller;

import com.example.progress.dto.response.HealthProxyResponseDTO;
import com.example.progress.dto.response.ProxyResponse;
import com.example.progress.external.HealthDetails;
import com.example.progress.service.MentalHealthService;
import com.example.progress.service.PhysicalHealthService;
import com.example.progress.service.ProgressProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/health")
public class HealthProxyController {
    private final PhysicalHealthService physicalHealthService;
    private final MentalHealthService mentalHealthService;
    private final ProgressProxyService progressProxyService;

    @PostMapping("/track")
    public Mono<ProxyResponse> addHealthProgress(@RequestBody HealthDetails healthDetails) {
        return Mono.fromRunnable(() -> {
            physicalHealthService.addPhysicalHealthProgress(healthDetails);
            mentalHealthService.addMentalHealthProgress(healthDetails);
        }).thenReturn(new ProxyResponse("progress is tracked"));
    }

    @GetMapping("/data-analysis")
    public HealthProxyResponseDTO getProgressHistory() {
        return progressProxyService.getDataForDataAnalysis();
    }
}
