package com.example.userservices.webclient;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.model.HealthDetails;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface IMentalHealthServiceClient {
    public Mono<ProxyResponse> trackMode(HealthDetails mode);
}
