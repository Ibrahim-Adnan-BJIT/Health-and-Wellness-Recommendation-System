package com.example.dataAnalysis.service;

import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;

import java.util.List;

public interface IMentalHealthService {
    public AnalysisByAgeResponseDTO analysisDepressionAmongAge(
            List<MentalHealthProxyDTO> progressHistoryDTOList);

    public AnalysisByAgeResponseDTO analysisStressLevelAmongAge(
            List<MentalHealthProxyDTO> progressHistoryDTOList);


}
