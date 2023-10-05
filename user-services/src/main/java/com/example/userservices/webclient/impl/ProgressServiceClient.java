package com.example.userservices.webclient.impl;

import com.example.userservices.DTO.response.ProxyResponse;
import com.example.userservices.model.HealthDetails;
import com.example.userservices.utils.Constants;
import com.example.userservices.webclient.IProgressServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ProgressServiceClient implements IProgressServiceClient {
    private final WebClient.Builder webClientBuilder;

    public ProgressServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("http://localhost:8081");
    }

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "healthDataFallback")
    @Override
    public Mono<ProxyResponse> addHealthProgress(HealthDetails healthDetails) {
        return webClientBuilder
                .build()
                .post()
                .uri("/api/v2/health/track")
                .bodyValue(healthDetails)
                .retrieve()
                .bodyToMono(ProxyResponse.class)
                .retryWhen(Retry.backoff(Constants.MAX_RETRY_ATTEMPTS, Duration.ofSeconds(20))) // Retry with a 20-second delay
                .onErrorResume(ex -> progressDataFallback(healthDetails, ex));
    }

    // Fallback method is a best practice for handling errors gracefully
    private Mono<? extends ProxyResponse> progressDataFallback(HealthDetails healthDetails, Throwable ex) {
        ProxyResponse response = new ProxyResponse();
        response.setMessage("Fallback: Service cant receive health data. Server down or error occurred.");
        return Mono.just(response);
    }
}
