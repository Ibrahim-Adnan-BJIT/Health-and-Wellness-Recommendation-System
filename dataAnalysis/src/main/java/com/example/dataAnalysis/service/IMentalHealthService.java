package com.example.dataAnalysis.service;

import com.example.dataAnalysis.dto.request.ProgressHistoryDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;

import java.util.List;

public interface IMentalHealthService {
    public AnalysisByAgeResponseDTO analysisDepressionAmongAge(
            List<ProgressHistoryDTO> progressHistoryDTOList, int noOfRecords);

    public AnalysisByAgeResponseDTO analysisStressLevelAmongAge(
            List<ProgressHistoryDTO> progressHistoryDTOList, int noOfRecords);



}
