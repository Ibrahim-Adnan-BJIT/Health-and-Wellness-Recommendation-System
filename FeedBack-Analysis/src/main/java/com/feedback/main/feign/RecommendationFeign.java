package com.feedback.main.feign;


import com.feedback.main.model.FeedbackResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "progress-service")
public interface RecommendationFeign {
    @GetMapping("/api/v2/feedback/get/user/{userId}")
    FeedbackResponseDTO getAllFeedbackByUserId(@PathVariable("userId") Long userId);
}
