package com.example.progress.service;

import com.example.progress.dto.response.AllFeedbackResponseDTO;

import java.util.List;

public interface FeedbackProxyService {
    public List<AllFeedbackResponseDTO> getSleepFeedback();

    public List<AllFeedbackResponseDTO> getDietFeedback();

    public List<AllFeedbackResponseDTO> getExerciseFeedback();

    public List<AllFeedbackResponseDTO> getMentalHealthFeedback();
}
