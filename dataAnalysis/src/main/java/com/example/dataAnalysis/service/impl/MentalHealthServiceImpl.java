package com.example.dataAnalysis.service.impl;

import com.example.dataAnalysis.dto.request.ProgressHistoryDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import com.example.dataAnalysis.dto.response.ResponseDTO;
import com.example.dataAnalysis.exception.CustomeException;
import com.example.dataAnalysis.external.enums.StressLevel;
import com.example.dataAnalysis.service.IMentalHealthService;
import org.springframework.http.HttpStatus;

import java.util.List;

public class MentalHealthServiceImpl implements IMentalHealthService {

    private class CountByAge {
        static int teenageCount;
        static int adultCount;
        static int seniorCount;
    }

    private void initCount() {
        CountByAge.teenageCount = 0;
        CountByAge.adultCount = 0;
        CountByAge.seniorCount = 0;
    }

    @Override
    public AnalysisByAgeResponseDTO analysisDepressionAmongAge(
            List<ProgressHistoryDTO> progressHistoryDTOList, int noOfRecords) {
        initCount();

        progressHistoryDTOList.forEach(this::analyzeByAge);

        return new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "Depression percentage among teenage: ",
                        (noOfRecords > 0) ? (double) CountByAge.teenageCount / noOfRecords : 0),
                new ResponseDTO(
                        "Depression percentage among adults: ",
                        (noOfRecords > 0) ? (double) CountByAge.adultCount / noOfRecords : 0),
                new ResponseDTO(
                        "Depression percentage among seniors: ",
                        (noOfRecords > 0) ? (double) CountByAge.seniorCount / noOfRecords : 0)
        );
    }

    private void analyzeByAge(ProgressHistoryDTO progressHistoryDTO) {
        if (progressHistoryDTO.getMentalHistoryDTO().isDepression()) {
            count(progressHistoryDTO.getMentalHistoryDTO().getAge());
        }
    }

    @Override
    public AnalysisByAgeResponseDTO analysisStressLevelAmongAge(
            List<ProgressHistoryDTO> progressHistoryDTOList, int noOfRecords) {
        initCount();
        progressHistoryDTOList.forEach(this::analyzeHighStressByAge);

        return new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "High stress percentage among teenage: ",
                        (noOfRecords > 0) ? (double) CountByAge.teenageCount / noOfRecords : 0),
                new ResponseDTO(
                        "High stress percentage among adults: ",
                        (noOfRecords > 0) ? (double) CountByAge.adultCount / noOfRecords : 0),
                new ResponseDTO(
                        "High stress percentage among seniors: ",
                        (noOfRecords > 0) ? (double) CountByAge.seniorCount / noOfRecords : 0)
        );
    }

    private void analyzeHighStressByAge(ProgressHistoryDTO progressHistoryDTO) {
        if (progressHistoryDTO.getMentalHistoryDTO().getStressLevel() == StressLevel.HIGH) {
            count(progressHistoryDTO.getMentalHistoryDTO().getAge());
        }
    }

    private void count(long age) {
        if (age > 12 && age < 20) {
            CountByAge.teenageCount++;
        } else if (age >= 20 && age < 40) {
            CountByAge.adultCount++;
        } else if (age >= 40) {
            CountByAge.seniorCount++;
        }
    }
}
