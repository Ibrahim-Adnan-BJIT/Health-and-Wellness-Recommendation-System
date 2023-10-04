package com.example.userservices.feignclient;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.feignclient.handleException.CustomFeignErrorDecoder;
import com.example.userservices.model.HealthDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Recommendation-Service", configuration = CustomFeignErrorDecoder.class)
public interface RecommendationsClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "healthDataFallback")
    @PostMapping("/api/v2/user/health-data")
    public ProxyResponse importUserHealthData(@RequestBody HealthDetails healthDetails);

    // Fallback method if any case service down
    default public ProxyResponse healthDataFallback(@RequestBody HealthDetails healthDetails, Throwable throwable) {
        ProxyResponse userInformation = new ProxyResponse();
        userInformation.setMessage("Service cant receive health data. Server down");

        return userInformation;
    }
}
