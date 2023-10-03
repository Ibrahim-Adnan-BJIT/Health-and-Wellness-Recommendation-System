package com.example.progress.service;

import java.util.List;

import com.example.progress.dto.request.FeedbackRequestDTO;
import com.example.progress.dto.response.FeedBackDTO;
import com.example.progress.entity.Feedback;

public interface FeedbackService {
    public void giveFeedback(long userId, long recommendationId, FeedbackRequestDTO feedbackRequestDTO);

    public List<FeedBackDTO> getFeedbackByUser(long userId);
}
