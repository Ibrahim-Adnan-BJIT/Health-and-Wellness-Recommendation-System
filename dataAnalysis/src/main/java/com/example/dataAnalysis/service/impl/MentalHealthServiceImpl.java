package com.example.dataAnalysis.service.impl;

import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import com.example.dataAnalysis.dto.response.ResponseDTO;
import com.example.dataAnalysis.external.enums.StressLevel;
import com.example.dataAnalysis.service.IMentalHealthService;
import org.springframework.stereotype.Service;
import com.example.dataAnalysis.utils.CountByAge;

import java.util.List;

@Service
public class MentalHealthServiceImpl implements IMentalHealthService {

    private void initCount() {
        CountByAge.teenageCount = 0;
        CountByAge.adultCount = 0;
        CountByAge.seniorCount = 0;
        CountByAge.NoOfTeeneage = 0;
        CountByAge.NoOfSenior = 0;
        CountByAge.NoOfAdult = 0;
    }

    @Override
    public AnalysisByAgeResponseDTO analysisDepressionAmongAge(
            List<MentalHealthProxyDTO> progressHistoryDTOList) {
        initCount();

        progressHistoryDTOList.forEach(this::analyzeByAge);

        return new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "Depression percentage among teenage: ",
                        (CountByAge.NoOfTeeneage > 0) ? (double) (CountByAge.teenageCount * 100) / CountByAge.NoOfTeeneage : 0),
                new ResponseDTO(
                        "Depression percentage among adults: ",
                        (CountByAge.NoOfAdult > 0) ? (double) (CountByAge.adultCount * 100) / CountByAge.NoOfAdult : 0),
                new ResponseDTO(
                        "Depression percentage among seniors: ",
                        (CountByAge.NoOfSenior > 0) ? (double) (CountByAge.seniorCount * 100) / CountByAge.NoOfSenior : 0)
        );
    }

    private void analyzeByAge(MentalHealthProxyDTO progressHistoryDTO) {
        if (progressHistoryDTO.isDepression()) {
            count(progressHistoryDTO.getAge());
        }
        ageCount(progressHistoryDTO.getAge());
    }

    @Override
    public AnalysisByAgeResponseDTO analysisStressLevelAmongAge(
            List<MentalHealthProxyDTO> progressHistoryDTOList) {
        initCount();
        progressHistoryDTOList.forEach(this::analyzeHighStressByAge);

        return new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "High stress percentage among teenage: ",
                        (CountByAge.NoOfTeeneage > 0) ? (double) (CountByAge.teenageCount * 100) / CountByAge.NoOfTeeneage : 0),
                new ResponseDTO(
                        "High stress percentage among adults: ",
                        (CountByAge.NoOfAdult > 0) ? (double) (CountByAge.adultCount * 100) / CountByAge.NoOfAdult : 0),
                new ResponseDTO(
                        "High stress percentage among seniors: ",
                        (CountByAge.NoOfSenior > 0) ? (double) (CountByAge.seniorCount * 100) / CountByAge.NoOfSenior : 0)
        );
    }

    private void analyzeHighStressByAge(MentalHealthProxyDTO progressHistoryDTO) {
        if (progressHistoryDTO.getStressLevel() == StressLevel.HIGH) {
            count(progressHistoryDTO.getAge());
        }
        ageCount(progressHistoryDTO.getAge());
    }

    private void count(long age) {
        CountByAge.teenageCount += (age > 12 && age < 20) ? 1 : 0;
        CountByAge.adultCount += (age >= 20 && age < 40) ? 1 : 0;
        CountByAge.seniorCount += (age >= 40) ? 1 : 0;
    }

    private void ageCount(long age) {
        CountByAge.NoOfTeeneage += (age > 12 && age < 20) ? 1 : 0;
        CountByAge.NoOfAdult += (age >= 20 && age < 40) ? 1 : 0;
        CountByAge.NoOfSenior += (age >= 40) ? 1 : 0;
    }
}
