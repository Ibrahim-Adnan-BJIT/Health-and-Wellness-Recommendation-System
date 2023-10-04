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
                .mentalHealthReview(mentalHealthFeedback.getReview())
                .exerciseFeedbackRating(exerciseFeedback.getRating())
                .exerciseReview(exerciseFeedback.getReview())
                .dietRecommendationRating(dietFeedback.getRating())
                .dietReview(dietFeedback.getReview())
                .sleepRecommendationRating(sleepFeedback.getRating())
                .sleepReview(sleepFeedback.getReview())
                .build();
    }

    public List<AllFeedbackResponseDTO> getSleepFeedback() {
        return sleepFeedbackRepository.findAll()
                .stream().map(this::mapToSleepDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getDietFeedback() {
        return dietFeedbackRepository.findAll()
                .stream().map(this::mapToDietDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getExerciseFeedback() {
        return exerciseFeedbackRepository.findAll()
                .stream().map(this::mapToExerciseDTO).toList();
    }

    public List<AllFeedbackResponseDTO> getMentalHealthFeedback() {
        return mentalHealthFeedbackRepository.findAll()
                .stream().map(this::mapToMentalDTO).toList();
    }

    private AllFeedbackResponseDTO mapToSleepDTO(SleepFeedback sleepFeedback) {
        return AllFeedbackResponseDTO.builder()
                .userId(sleepFeedback.getUserId())
                .rating(sleepFeedback.getRating())
                .review(sleepFeedback.getReview()).build();
    }

    private AllFeedbackResponseDTO mapToDietDTO(DietFeedback dietFeedback) {
        return AllFeedbackResponseDTO.builder()
                .userId(dietFeedback.getUserId())
                .rating(dietFeedback.getRating())
                .review(dietFeedback.getReview()).build();
    }

    private AllFeedbackResponseDTO mapToExerciseDTO(ExerciseFeedback exerciseFeedback) {
        return AllFeedbackResponseDTO.builder()
                .userId(exerciseFeedback.getUserId())
                .rating(exerciseFeedback.getRating())
                .review(exerciseFeedback.getReview())
                .build();
    }

    private AllFeedbackResponseDTO mapToMentalDTO(MentalHealthFeedback mentalHealthFeedback) {
        return AllFeedbackResponseDTO.builder()
                .userId(mentalHealthFeedback.getUserId())
                .rating(mentalHealthFeedback.getRating())
                .review(mentalHealthFeedback.getReview())
                .build();
    }

}
