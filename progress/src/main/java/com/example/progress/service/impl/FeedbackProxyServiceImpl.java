package com.example.progress.service.impl;

import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.entity.*;
import com.example.progress.repository.DietFeedbackRepository;
import com.example.progress.repository.ExerciseFeedbackRepository;
import com.example.progress.repository.MentalHealthFeedbackRepository;
import com.example.progress.repository.SleepFeedbackRepository;
import com.example.progress.service.FeedbackProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackProxyServiceImpl implements FeedbackProxyService {
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

}
