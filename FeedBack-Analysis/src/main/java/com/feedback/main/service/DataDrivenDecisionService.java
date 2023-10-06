package com.feedback.main.service;

import com.feedback.main.dietResponse.DietFeedbackDecisionResponse;
import com.feedback.main.exception.CustomException;
import com.feedback.main.model.AllFeedbackResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DataDrivenDecisionService {
    @Autowired
    private RestTemplate restTemplate;

    public DietFeedbackDecisionResponse dietFeedbackDecisions() {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/diet/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);

        if (feedbackDtos != null && feedbackDtos.length > 0) {
            double averageRating = calculateAverageRating(Arrays.asList(feedbackDtos));

            String feedbackMessage;
            if (averageRating >= 7.0) {
                feedbackMessage = "Great feedback! We should keep up the good work.";
            } else if (averageRating >= 5) {
                feedbackMessage = "Good feedback. But There is room for improvement.";
            } else {
                feedbackMessage = "Feedback indicates improvement is needed.";
            }

            return new DietFeedbackDecisionResponse(feedbackMessage, averageRating);
        } else {
            throw new CustomException("Here is no diet Feedback yet");
        }
    }


    public DietFeedbackDecisionResponse sleepFeedbackDecisions() {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/sleep/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);

        if (feedbackDtos != null && feedbackDtos.length > 0) {
            double averageRating = calculateAverageRating(Arrays.asList(feedbackDtos));

            String feedbackMessage;
            if (averageRating >= 7.0) {
                feedbackMessage = "Great feedback! We should keep up the good work.";
            } else if (averageRating >= 5) {
                feedbackMessage = "Good feedback. There is room for improvement.";
            } else {
                feedbackMessage = "Feedback indicates improvement is needed.";
            }

            return new DietFeedbackDecisionResponse(feedbackMessage, averageRating);
        } else {
            throw new CustomException("Here is no sleep Feedback yet");
        }
    }


    public DietFeedbackDecisionResponse exerciseFeedbackDecisions() {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/exercise/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);

        if (feedbackDtos != null && feedbackDtos.length > 0) {
            double averageRating = calculateAverageRating(Arrays.asList(feedbackDtos));

            String feedbackMessage;
            if (averageRating >= 7.0) {
                feedbackMessage = "Great feedback! We should keep up the good work.";
            } else if (averageRating >= 5) {
                feedbackMessage = "Good feedback. There is room for improvement.";
            } else {
                feedbackMessage = "Feedback indicates improvement is needed.";
            }

            return new DietFeedbackDecisionResponse(feedbackMessage, averageRating);
        } else {
            throw new CustomException("Here is no sleep Feedback yet");
        }
    }


    public DietFeedbackDecisionResponse mentalHealthFeedbackDecisions() {
        String remoteEndpoint = "http://localhost:8081/api/v2/feedback/mental-health/get";
        AllFeedbackResponseDTO[] feedbackDtos = restTemplate.getForObject(remoteEndpoint, AllFeedbackResponseDTO[].class);

        if (feedbackDtos != null && feedbackDtos.length > 0) {
            double averageRating = calculateAverageRating(Arrays.asList(feedbackDtos));

            String feedbackMessage;
            if (averageRating >= 7.0) {
                feedbackMessage = "Great feedback! We should keep up the good work.";
            } else if (averageRating >= 5) {
                feedbackMessage = "Good feedback. There is room for improvement.";
            } else {
                feedbackMessage = "Feedback indicates improvement is needed.";
            }

            return new DietFeedbackDecisionResponse(feedbackMessage, averageRating);
        } else {
            throw new CustomException("Here is no sleep Feedback yet");
        }
    }

    private double calculateAverageRating(List<AllFeedbackResponseDTO> feedbackDtos) {
        if (feedbackDtos == null || feedbackDtos.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;

        for (AllFeedbackResponseDTO feedbackDto : feedbackDtos) {
            double rating = feedbackDto.getRating();
            sum += rating;
            count++;
        }
        return sum / count;
    }


}
