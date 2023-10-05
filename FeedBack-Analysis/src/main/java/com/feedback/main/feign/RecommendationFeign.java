package com.feedback.main.feign;


import com.feedback.main.model.AllFeedbackResponseDTO;
import com.feedback.main.model.FeedbackResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "progress-service")
public interface RecommendationFeign {
    @GetMapping("/api/v2/feedback/get/user/{userId}")
    public FeedbackResponseDTO getAllFeedbackByUserId(@PathVariable("userId") Long userId);
}
