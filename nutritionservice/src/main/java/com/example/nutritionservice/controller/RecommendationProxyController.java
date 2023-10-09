package com.example.nutritionservice.controller;

import com.example.nutritionservice.dto.response.ProxyResponse;
import com.example.nutritionservice.external.HealthDetails;
import com.example.nutritionservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/recommendation")
public class RecommendationProxyController {
    private final RecommendationService recommendationService;

    @PostMapping("/diet/add")
    public Mono<ProxyResponse> addRecommendation(@RequestBody HealthDetails healthDetails) {
        return Mono.fromRunnable(() -> recommendationService.addRecommendation(healthDetails))
                .thenReturn(new ProxyResponse("recommendation successful"));
    }
}
