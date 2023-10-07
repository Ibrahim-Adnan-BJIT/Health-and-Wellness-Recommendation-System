package com.example.dataAnalysis.service.impl;

import com.example.dataAnalysis.dto.response.DataAnalysisResponseDTO;
import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import com.example.dataAnalysis.dto.response.HealthProxyResponseDTO;
import com.example.dataAnalysis.exception.CustomeException;
import com.example.dataAnalysis.feign.RecommendationFeign;
import com.example.dataAnalysis.service.IDataAnalysisService;
import com.example.dataAnalysis.service.IMentalHealthService;
import com.example.dataAnalysis.service.IPhysicalHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataAnalysisServiceImpl implements IDataAnalysisService {
    private final RecommendationFeign recommendationFeign;
    private final IMentalHealthService mentalHealthService;
    private final IPhysicalHealthService physicalHealthService;

    @Override
    public DataAnalysisResponseDTO dataAnalysis() {
        HealthProxyResponseDTO healthProxyResponseDTO = recommendationFeign.getHealthProxyInformation();
        int noOfRecords = checkNoOfRecords(healthProxyResponseDTO.getMentalHealthProxyDTOS());

        AnalysisByAgeResponseDTO depressionByAge = mentalHealthService.analysisDepressionAmongAge(
                healthProxyResponseDTO.getMentalHealthProxyDTOS());

        AnalysisByAgeResponseDTO highStressByAge = mentalHealthService.analysisStressLevelAmongAge(
                healthProxyResponseDTO.getMentalHealthProxyDTOS());

        AnalysisByAgeResponseDTO sleepIssueByAge = physicalHealthService.AnalysisByAgeSleepIssue(
                healthProxyResponseDTO.getPhysicalHealthProxyDTOS()
        );

        List<AnalysisByAgeResponseDTO> bloodPressureIssueByAge = physicalHealthService.AnalysisByAgeBloodPressure(
                healthProxyResponseDTO.getPhysicalHealthProxyDTOS()
        );

        return DataAnalysisResponseDTO.builder()
                .depressionAnalysis(depressionByAge)
                .stressAnalysis(highStressByAge)
                .sleepIssueAnalysis(sleepIssueByAge)
                .bloodPressureAnalysis(bloodPressureIssueByAge)
                .build();
    }

    private int checkNoOfRecords(List<MentalHealthProxyDTO> progressHistoryDTOList) {
        if (progressHistoryDTOList.size() < 10) {
            throw new CustomeException(HttpStatus.BAD_REQUEST,
                    "Data is insufficient for analysis");
        }
        return progressHistoryDTOList.size();
    }
}
