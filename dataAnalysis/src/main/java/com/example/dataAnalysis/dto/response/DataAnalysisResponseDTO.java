package com.example.dataAnalysis.dto.response;

import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisResponseDTO {
    private AnalysisByAgeResponseDTO depressionAnalysis;
    private AnalysisByAgeResponseDTO stressAnalysis;
    private AnalysisByAgeResponseDTO sleepIssueAnalysis;
    private List<AnalysisByAgeResponseDTO> bloodPressureAnalysis;
}
