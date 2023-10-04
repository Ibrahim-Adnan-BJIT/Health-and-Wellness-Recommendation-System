package com.example.userservices.webclient.impl;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.model.HealthDetails;
import com.example.userservices.utils.Constants;
import com.example.userservices.webclient.RecommendationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class RecommendationServiceClientImpl implements RecommendationServiceClient {
    private final WebClient.Builder webClientBuilder;

    public RecommendationServiceClientImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("http://localhost:8203");
    }
    @Override
    public Mono<ProxyResponse> importUserHealthData(HealthDetails healthDetails) {
        return webClientBuilder
                .build()
                .post()
                .uri("/api/v2/user/health-data")
                .bodyValue(healthDetails)
                .retrieve()
                .bodyToMono(ProxyResponse.class)
                .retryWhen(Retry.backoff(Constants.MAX_RETRY_ATTEMPTS, Duration.ofSeconds(20))) // Retry with a 20-second delay
                .onErrorResume(ex -> healthDataFallback(healthDetails, ex));
    }

    // Fallback method is a best practice for handling errors gracefully
    private Mono<? extends ProxyResponse> healthDataFallback(HealthDetails healthDetails, Throwable ex) {
        ProxyResponse response = new ProxyResponse();
        response.setMessage("Fallback: Service cant receive health data. Server down or error occurred.");
        return Mono.just(response);
    }
}
