package com.example.dataAnalysis.feign;

import com.example.dataAnalysis.dto.request.ProgressHistoryDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "progress-service", configuration = FeignErrorDecoder.class)
public interface RecommendationFeign {

    @CircuitBreaker(name = "CircuitBreakerService")
    @GetMapping("/api/v2/proxyUser/health-data")
    public List<ProgressHistoryDTO> getHealthProxyInformation();
}
