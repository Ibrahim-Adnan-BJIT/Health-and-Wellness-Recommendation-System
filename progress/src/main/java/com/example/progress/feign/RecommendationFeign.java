package com.example.progress.feign;

import com.example.progress.external.HealthDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", configuration = FeignErrorDecoder.class)
public interface RecommendationFeign {

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallGetHealthProxyInformation")
    @GetMapping("/api/v2/proxyUser/health-data/{userId}")
    public HealthDetails getHealthProxyInformation(@PathVariable("userId") long userId);

    default public HealthDetails fallGetHealthProxyInformation(@PathVariable long userID,
                                                          Throwable throwable) {
        return new HealthDetails();
    }

    @GetMapping("/api/v2/proxyUser/health-data/{userId}")
    public HealthDetails getFeedbackProxyInformation(@PathVariable("userId") long userId);

}
