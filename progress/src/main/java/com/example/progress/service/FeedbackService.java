package com.example.progress.service;

import java.util.List;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;
import com.example.progress.entity.DietFeedback;

public interface FeedbackService {
    public void addDietFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO);

    public void addExerciseFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO);

    public void addSleepFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO);

    public void addMentalHealthFeedback(long userId, FeedbackRequestDTO feedbackRequestDTO);

    public FeedbackResponseDTO getFeedbackByUser(long userId);
}
