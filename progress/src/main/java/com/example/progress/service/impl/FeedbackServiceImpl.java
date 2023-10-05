package com.example.progress.service.impl;

import java.util.Date;
import java.util.List;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.entity.DietFeedback;
import com.example.progress.entity.ExerciseFeedback;
import com.example.progress.entity.MentalHealthFeedback;
import com.example.progress.entity.SleepFeedback;
import com.example.progress.exception.CustomException;
import com.example.progress.external.HealthDetails;
import com.example.progress.feign.RecommendationFeign;
import com.example.progress.repository.DietFeedbackRepository;
import com.example.progress.repository.ExerciseFeedbackRepository;
import com.example.progress.repository.MentalHealthFeedbackRepository;
import com.example.progress.repository.SleepFeedbackRepository;
import com.example.progress.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final RecommendationFeign recommendationFeign;

    private final DietFeedbackRepository dietFeedbackRepository;
    private final ExerciseFeedbackRepository exerciseFeedbackRepository;
    private final MentalHealthFeedbackRepository mentalHealthFeedbackRepository;
    private final SleepFeedbackRepository sleepFeedbackRepository;

    public void addDietFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        DietFeedback feedback = giveDietFeedback(userId, feedbackRequestDTO);
        dietFeedbackRepository.save(feedback);
    }

    @Override
    public void addExerciseFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        ExerciseFeedback feedback = giveExerciseFeedback(userId, feedbackRequestDTO);
        exerciseFeedbackRepository.save(feedback);
    }

    @Override
    public void addSleepFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        SleepFeedback feedback = giveSleepFeedback(userId, feedbackRequestDTO);
        sleepFeedbackRepository.save(feedback);
    }

    @Override
    public void addMentalHealthFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        MentalHealthFeedback feedback = giveMentalHealthFeedback(userId, feedbackRequestDTO);
        mentalHealthFeedbackRepository.save(feedback);
    }

    private DietFeedback giveDietFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        DietFeedback dietFeedback = dietFeedbackRepository.findByUserId(userId)
                .orElse(new DietFeedback());
        if (dietFeedback.getUserId() != userId) {
            checkUserHealth(userId, feedbackRequestDTO);
        }

        dietFeedback.setUserId(userId);
        dietFeedback.setRating(feedbackRequestDTO.getRating());
        dietFeedback.setReview(feedbackRequestDTO.getReview());

        return dietFeedback;
    }

    private ExerciseFeedback giveExerciseFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        ExerciseFeedback exerciseFeedback = exerciseFeedbackRepository.findByUserId(userId)
                .orElse(new ExerciseFeedback());
        if (exerciseFeedback.getUserId() != userId) {
            checkUserHealth(userId, feedbackRequestDTO);
        }

        exerciseFeedback.setUserId(userId);
        exerciseFeedback.setRating(feedbackRequestDTO.getRating());
        exerciseFeedback.setReview(feedbackRequestDTO.getReview());

        return exerciseFeedback;
    }

    private MentalHealthFeedback giveMentalHealthFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        MentalHealthFeedback mentalHealthFeedback = mentalHealthFeedbackRepository.findByUserId(userId)
                .orElse(new MentalHealthFeedback());
        if (mentalHealthFeedback.getUserId() != userId) {
            checkUserHealth(userId, feedbackRequestDTO);
        }

        mentalHealthFeedback.setUserId(userId);
        mentalHealthFeedback.setRating(feedbackRequestDTO.getRating());
        mentalHealthFeedback.setReview(feedbackRequestDTO.getReview());

        return mentalHealthFeedback;
    }

    private SleepFeedback giveSleepFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        SleepFeedback sleepHealthFeedback = sleepFeedbackRepository.findByUserId(userId)
                .orElse(new SleepFeedback());
        if (sleepHealthFeedback.getUserId() != userId) {
            checkUserHealth(userId, feedbackRequestDTO);
        }

        sleepHealthFeedback.setUserId(userId);
        sleepHealthFeedback.setRating(feedbackRequestDTO.getRating());
        sleepHealthFeedback.setReview(feedbackRequestDTO.getReview());

        return sleepHealthFeedback;
    }

    private void checkUserHealth(long userId, FeedbackRequestDTO feedbackRequestDTO) {
        HealthDetails healthDetails = recommendationFeign.getHealthProxyInformation(userId);
        if (healthDetails.getUserId() != userId) {
            throw new CustomException(new Date(), "complete the profile info", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public FeedbackResponseDTO getFeedbackByUser(long userId) {
        recommendationFeign.getHealthProxyInformation(userId);

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
