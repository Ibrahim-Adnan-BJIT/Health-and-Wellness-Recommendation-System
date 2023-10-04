package com.example.userservices.feing;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.DTO.response.UserInformation;
import com.example.userservices.feing.handleException.CustomFeignErrorDecoder;
import com.example.userservices.model.HealthDetails;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Recommendation-Service", configuration = CustomFeignErrorDecoder.class)
public interface RecommendationsClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "healthDataFallback")
    @PostMapping("/api/v2/user/health-data")
    public ProxyResponse importUserHealthData(@RequestBody HealthDetails healthDetails);

    // Fallback method if any case service down
    default public ProxyResponse healthDataFallback(@RequestBody HealthDetails healthDetails, Throwable throwable) {
        // This is the fallback method that will be called when CircuitBreaker is open or an exception occurs.
        ProxyResponse userInformation = new ProxyResponse();
        userInformation.setMessage("Service cant receive health data. Server down");

        return userInformation;
    }
}
