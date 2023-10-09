package com.example.userservices.feignclient;

import com.example.userservices.DTO.request.LogInRequestDTO;
import com.example.userservices.DTO.request.RegisterRequestDTO;
import com.example.userservices.DTO.response.AuthenticationResponseDTO;
import com.example.userservices.DTO.response.LogInResponseDTO;
import com.example.userservices.DTO.response.UserInformation;
import com.example.userservices.feignclient.handleException.CustomFeignErrorDecoder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SECURITY-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface SecurityServiceClient {
    @CircuitBreaker(name = "CircuitBreakerService")
    @PostMapping("/api/v2/auth/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request);

    @CircuitBreaker(name = "CircuitBreakerService")
    @PostMapping("/api/v2/auth/login")
    public LogInResponseDTO authenticate(@RequestBody LogInRequestDTO request);


    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "getUserInformationFallback")
    @GetMapping("/api/v2/user/user-information/{userId}")
    public UserInformation getUserInformation(@PathVariable long userId);

    default public UserInformation getUserInformationFallback(@PathVariable long userId, Throwable throwable) {
        // This is the fallback method that will be called when CircuitBreaker is open or an exception occurs.
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername("Dummy User Name");
        userInformation.setEmail("Dummy User Email");

        return userInformation;
    }
}
