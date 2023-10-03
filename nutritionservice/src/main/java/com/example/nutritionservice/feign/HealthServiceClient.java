package com.example.nutritionservice.feign;

import com.example.nutritionservice.exception.FeignErrorDecoder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "User-Service", configuration = FeignErrorDecoder.class)
public interface HealthServiceClient {
//    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallbackGetHealthInfo")
//    @GetMapping("/user/getuserbyid/{userId}")
//    public HealthDetailsDto getHealthInfo(@PathVariable(name = "userId") int userId);
//
//    default HealthDetailsDto fallbackGetHealthInfo(int userId, Throwable throwable) {
//        System.out.println("user fall back ......");
//        return new ResponseEntity<>(new UserResponseDto(), HttpStatus.BAD_REQUEST);
//    }
}
