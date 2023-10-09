package com.example.progress.service.impl;

import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.entity.*;
import com.example.progress.exception.CustomException;
import com.example.progress.exception.FeignException;
import com.example.progress.feign.RecommendationFeign;
import com.example.progress.repository.DietFeedbackRepository;
import com.example.progress.repository.ExerciseFeedbackRepository;
import com.example.progress.repository.MentalHealthFeedbackRepository;
import com.example.progress.repository.SleepFeedbackRepository;
import com.example.progress.service.FeedbackProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackProxyServiceImpl implements FeedbackProxyService {
    private final RecommendationFeign recommendationFeign;
    private final DietFeedbackRepository dietFeedbackRepository;
    private final ExerciseFeedbackRepository exerciseFeedbackRepository;
    private final MentalHealthFeedbackRepository mentalHealthFeedbackRepository;
    private final SleepFeedbackRepository sleepFeedbackRepository;

    public List<AllFeedbackResponseDTO> getSleepFeedback() {
        return sleepFeedbackRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getDietFeedback() {
        return dietFeedbackRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getExerciseFeedback() {
        return exerciseFeedbackRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getMentalHealthFeedback() {
        return mentalHealthFeedbackRepository.findAll()
                .stream().map(this::mapToDTO).toList();
    }

    private AllFeedbackResponseDTO mapToDTO(FeedbackParent sleepFeedback) {
        return AllFeedbackResponseDTO.builder()
                .userId(sleepFeedback.getUserId())
                .rating(sleepFeedback.getRating())
                .review(sleepFeedback.getReview()).build();
    }

    @Override
    public FeedbackResponseDTO getFeedbackByUser(long userId) {
        try {
            recommendationFeign.getFeedbackProxyInformation(userId);
        } catch (FeignException ex) {
            throw new CustomException(ex.getTimestamp(), "User Feedback Not Found", HttpStatus.NOT_FOUND);
        }

        SleepFeedback sleepFeedback = sleepFeedbackRepository.findByUserId(userId)
                .orElse(new SleepFeedback());

        ExerciseFeedback exerciseFeedback = exerciseFeedbackRepository.findByUserId(userId)
                .orElse(new ExerciseFeedback());

        DietFeedback dietFeedback = dietFeedbackRepository.findByUserId(userId)
                .orElse(new DietFeedback());

        MentalHealthFeedback mentalHealthFeedback = mentalHealthFeedbackRepository.findByUserId(userId)
                .orElse(new MentalHealthFeedback());

        return FeedbackResponseDTO
                .builder()
                .mentalHealthRecommendationRating(mentalHealthFeedback.getRating())
                .mentalHealthReview(mentalHealthFeedback.getReview() == null ?
                        "Review not given yet" : mentalHealthFeedback.getReview())
                .exerciseFeedbackRating(exerciseFeedback.getRating())
                .exerciseReview(exerciseFeedback.getReview() == null ?
                        "Review not given yet" : exerciseFeedback.getReview())
                .dietRecommendationRating(dietFeedback.getRating())
                .dietReview(dietFeedback.getReview() == null ?
                        "Review not given yet" : dietFeedback.getReview())
                .sleepRecommendationRating(sleepFeedback.getRating())
                .sleepReview(sleepFeedback.getReview() == null ?
                        "Review not given yet" : sleepFeedback.getReview())
                .build();
    }

}
