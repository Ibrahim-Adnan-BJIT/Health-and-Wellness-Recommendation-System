package com.feedback.main.service;

import com.feedback.main.exception.CustomException;
import com.feedback.main.feign.RecommendationFeign;
import com.feedback.main.model.AllFeedbackResponseDTO;
import com.feedback.main.model.FeedbackResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FeedbackAnalysisDashboardService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RecommendationFeign recommendationFeign;



    public List<AllFeedbackResponseDTO> getAllFeedbackFromDiet() throws CustomException {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/diet/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);
        if (feedbackDtos != null && feedbackDtos.length > 0) {
            return Arrays.asList(feedbackDtos);
        } else {
            throw new CustomException("Here is no diet Feedback yet");
        }

    }


    public List<AllFeedbackResponseDTO> getAllFeedbackFromSleep() throws CustomException {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/sleep/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);
        if (feedbackDtos != null && feedbackDtos.length > 0) {
            return Arrays.asList(feedbackDtos);
        } else {
            throw new CustomException("Here is no sleep Feedback yet");
        }

    }


    public List<AllFeedbackResponseDTO> getAllFeedbackFromExercises() throws CustomException {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/exercise/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);
        if (feedbackDtos != null && feedbackDtos.length > 0) {
            return Arrays.asList(feedbackDtos);
        } else {
            throw new CustomException("Here is no exercise Feedback yet");
        }

    }


    public List<AllFeedbackResponseDTO> getAllFeedbackFromMentalHealth() throws CustomException {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/mental-health/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);
        if (feedbackDtos != null && feedbackDtos.length > 0) {
            return Arrays.asList(feedbackDtos);
        } else {
            throw new CustomException("Here is no mental health Feedback yet");
        }

    }


    public FeedbackResponseDTO getAllFeedbackByUserId(Long userId) throws CustomException {
        return recommendationFeign.getAllFeedbackByUserId(userId);
    }

}
