package com.example.progress.service;

import com.example.progress.dto.response.AllFeedbackResponseDTO;
import com.example.progress.dto.response.FeedbackResponseDTO;

import java.util.List;

public interface FeedbackProxyService {
    public List<AllFeedbackResponseDTO> getSleepFeedback();

    public List<AllFeedbackResponseDTO> getDietFeedback();

    public List<AllFeedbackResponseDTO> getExerciseFeedback();

    public List<AllFeedbackResponseDTO> getMentalHealthFeedback();

    public FeedbackResponseDTO getFeedbackByUser(long userId);
}
