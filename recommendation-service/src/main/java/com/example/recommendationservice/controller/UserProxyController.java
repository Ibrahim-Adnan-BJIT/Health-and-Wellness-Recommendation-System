package com.example.recommendationservice.controller;

import com.example.recommendationservice.DTO.response.ProxyResponse;
import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.services.IDataProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/user")
public class UserProxyController {

    private final IDataProcessService healthDetailsService;

    public UserProxyController(IDataProcessService healthDetailsService) {
        this.healthDetailsService = healthDetailsService;
    }

    // Internal Api for push data when any user create profile or update Health information in User Microservice
    @PostMapping("/health-data")
    public Mono<ProxyResponse> importUserHealthData(@RequestBody HealthDetails healthDetails) {

        return Mono.fromRunnable(() -> healthDetailsService.importHealthDetails(healthDetails))
                .thenReturn(new ProxyResponse("Data received successfully"));
    }
}
