package com.example.progress.controller;

import com.example.progress.dto.response.ProxyResponse;
import com.example.progress.external.HealthDetails;
import com.example.progress.service.MentalHealthService;
import com.example.progress.service.PhysicalHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/health")
public class HealthProxyController {
    private final PhysicalHealthService physicalHealthService;
    private final MentalHealthService mentalHealthService;

    @PostMapping("/track")
    public Mono<ProxyResponse> addHealthProgress(@RequestBody HealthDetails healthDetails) {
        return Mono.fromRunnable(() -> {
            physicalHealthService.addPhysicalHealthProgress(healthDetails);
            mentalHealthService.addMentalHealthProgress(healthDetails);
        }).thenReturn(new ProxyResponse("progress is tracked"));
    }
}
