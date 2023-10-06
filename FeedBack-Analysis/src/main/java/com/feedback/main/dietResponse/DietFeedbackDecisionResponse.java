package com.feedback.main.dietResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietFeedbackDecisionResponse {
    private String feedbackMessage;
    private double averageRating;

}
