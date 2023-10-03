package com.example.progress.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Recommendation-Service", configuration = FeignErrorDecoder.class)
public interface RecommendationFeign {

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallIsRecommendationExist")
    @GetMapping("api/v2/check-recommendation/user/{userID}/recommendation/{recommendationId}")
    public boolean isRecommendationExist(@PathVariable long userID,
                                         @PathVariable long recommendationId);


}
