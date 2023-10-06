package com.example.dataAnalysis.feign;

import com.example.dataAnalysis.dto.response.HealthProxyResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "progress-service", configuration = FeignErrorDecoder.class)
public interface RecommendationFeign {

    @CircuitBreaker(name = "CircuitBreakerService")
    @GetMapping("/api/v2/health/data-analysis")
    public HealthProxyResponseDTO getHealthProxyInformation();
}
