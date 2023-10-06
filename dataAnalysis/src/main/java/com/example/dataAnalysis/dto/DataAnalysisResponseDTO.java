package com.example.dataAnalysis.dto;

import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisResponseDTO {
    private AnalysisByAgeResponseDTO depressionAnalysis;
    private AnalysisByAgeResponseDTO stressAnalysis;
}
