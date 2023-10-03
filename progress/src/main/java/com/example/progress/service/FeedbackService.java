package com.example.progress.service;

import java.util.List;

import com.example.progress.dto.response.FeedBackDTO;

public interface FeedbackService {
	public void giveFeedback(long userId, long recommendationId);
	
	public List<FeedBackDTO> getFeedbackByUser(long userId);
}
