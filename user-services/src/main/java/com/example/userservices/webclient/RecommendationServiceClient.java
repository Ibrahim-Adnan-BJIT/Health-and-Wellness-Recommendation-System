package com.example.userservices.webclient;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.model.HealthDetails;
import reactor.core.publisher.Mono;

public interface RecommendationServiceClient {
    Mono<ProxyResponse> importUserHealthData(HealthDetails healthDetails);
}
