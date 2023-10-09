package com.example.dataAnalysis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisByAgeResponseDTO {
    private ResponseDTO teenagePercentage;
    private ResponseDTO adultPercentage;

    //up to 65
    private ResponseDTO seniorPercentage;
}
