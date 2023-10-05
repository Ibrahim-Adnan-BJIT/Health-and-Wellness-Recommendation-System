package com.example.progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private double mentalHealthRecommendationRating;
    private double dietRecommendationRating;
    private double sleepRecommendationRating;
    private double exerciseFeedbackRating;

    private String mentalHealthReview;
    private String dietReview;
    private String sleepReview;
    private String exerciseReview;
}
