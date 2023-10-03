package com.example.progress.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Recommendation-Service", configuration = FeignErrorDecoder.class)
public interface RecommendationFeign {

}
