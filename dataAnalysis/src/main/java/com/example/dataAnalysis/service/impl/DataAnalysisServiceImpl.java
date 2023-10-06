package com.example.dataAnalysis.service.impl;

import com.example.dataAnalysis.dto.DataAnalysisResponseDTO;
import com.example.dataAnalysis.dto.request.ProgressHistoryDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import com.example.dataAnalysis.exception.CustomeException;
import com.example.dataAnalysis.feign.RecommendationFeign;
import com.example.dataAnalysis.service.IDataAnalysisService;
import com.example.dataAnalysis.service.IMentalHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataAnalysisServiceImpl implements IDataAnalysisService {
    private final RecommendationFeign recommendationFeign;
    private final IMentalHealthService mentalHealthService;

    @Override
    public DataAnalysisResponseDTO dataAnalysis() {
        List<ProgressHistoryDTO> progressHistoryDTOList = recommendationFeign.getHealthProxyInformation();
        int noOfRecords = checkNoOfRecords(progressHistoryDTOList);

        AnalysisByAgeResponseDTO depressionByAge = mentalHealthService.
                analysisDepressionAmongAge(progressHistoryDTOList, noOfRecords);

        AnalysisByAgeResponseDTO highStressByAge = mentalHealthService.
                analysisDepressionAmongAge(progressHistoryDTOList, noOfRecords);

        return DataAnalysisResponseDTO.builder()
                .depressionAnalysis(depressionByAge)
                .stressAnalysis(highStressByAge)
                .build();
    }

    private int checkNoOfRecords(List<ProgressHistoryDTO> progressHistoryDTOList) {
        if (progressHistoryDTOList.size() < 10) {
            throw new CustomeException(HttpStatus.BAD_REQUEST,
                    "Data is insufficient for analysis");
        }
        return progressHistoryDTOList.size();
    }
}
