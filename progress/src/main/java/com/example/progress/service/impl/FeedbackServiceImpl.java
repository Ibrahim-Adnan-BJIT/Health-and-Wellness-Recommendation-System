package com.example.progress.service.impl;

import java.util.List;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedBackDTO;
import com.example.progress.entity.Feedback;
import com.example.progress.feign.RecommendationFeign;
import com.example.progress.repository.FeedbackRepository;
import com.example.progress.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final RecommendationFeign recommendationFeign;
    private final FeedbackRepository feedbackRepository;

    @Override
    public void giveFeedback(long userId, long recommendationId, FeedbackRequestDTO feedbackRequestDTO) {
        if (!recommendationFeign.isRecommendationExist(userId, recommendationId)) {
        }
        Feedback feedback = Feedback.builder()
                .userId(userId)
                .recommendationId(feedbackRequestDTO.getRecommendationId())
                .rating(feedbackRequestDTO.getRating())
                .review(feedbackRequestDTO.getReview())
                .build();
        feedbackRepository.save(feedback);
    }

    @Override
    public List<FeedBackDTO> getFeedbackByUser(long userId) {

        return null;
    }

}
