package com.example.dataAnalysis.service;

import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.request.PhysicalHealthProxyDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;

import java.util.List;

public interface IPhysicalHealthService {
    public AnalysisByAgeResponseDTO AnalysisByAgeSleepIssue(
            List<PhysicalHealthProxyDTO> progressHistoryDTOList);

    public List<AnalysisByAgeResponseDTO> AnalysisByAgeBloodPressure(
            List<PhysicalHealthProxyDTO> progressHistoryDTOList);
}
