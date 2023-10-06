package com.example.dataAnalysis.service.impl;

import com.example.dataAnalysis.dto.request.MentalHealthProxyDTO;
import com.example.dataAnalysis.dto.request.PhysicalHealthProxyDTO;
import com.example.dataAnalysis.dto.response.AnalysisByAgeResponseDTO;
import com.example.dataAnalysis.dto.response.ResponseDTO;
import com.example.dataAnalysis.external.enums.BloodPressure;
import com.example.dataAnalysis.external.enums.SleepIssue;
import com.example.dataAnalysis.external.enums.StressLevel;
import com.example.dataAnalysis.service.IPhysicalHealthService;
import com.example.dataAnalysis.utils.CountByAge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalHealthServiceImpl implements IPhysicalHealthService {
    private void initCount() {
        CountByAge.teenageCount = 0;
        CountByAge.adultCount = 0;
        CountByAge.seniorCount = 0;
        CountByAge.NoOfTeeneage = 0;
        CountByAge.NoOfSenior = 0;
        CountByAge.NoOfAdult = 0;
    }

    @Override
    public AnalysisByAgeResponseDTO AnalysisByAgeSleepIssue(
            List<PhysicalHealthProxyDTO> progressHistoryDTOList) {
        initCount();

        progressHistoryDTOList.forEach(this::analyzeByAge);

        return new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "Sleep issue among teenage: ",
                        (CountByAge.NoOfTeeneage > 0) ? (double) (CountByAge.teenageCount * 100) / CountByAge.NoOfTeeneage : 0),
                new ResponseDTO(
                        "Sleep issue among adults: ",
                        (CountByAge.NoOfAdult > 0) ? (double) (CountByAge.adultCount * 100) / CountByAge.NoOfAdult : 0),
                new ResponseDTO(
                        "Sleep issue among seniors: ",
                        (CountByAge.NoOfSenior > 0) ? (double) (CountByAge.seniorCount * 100) / CountByAge.NoOfSenior : 0)
        );
    }

    private void analyzeByAge(PhysicalHealthProxyDTO progressHistoryDTO) {
        if (progressHistoryDTO.getSleepIssue() != SleepIssue.NONE) {
            count(progressHistoryDTO.getAge());
        }
        ageCount(progressHistoryDTO.getAge());
    }

    @Override
    public List<AnalysisByAgeResponseDTO> AnalysisByAgeBloodPressure(
            List<PhysicalHealthProxyDTO> progressHistoryDTOList) {
        initCount();
        progressHistoryDTOList.forEach(this::analyzeHighBloodPressureByAge);

        List<AnalysisByAgeResponseDTO> analysisByAgeResponseDTOS = new ArrayList<>();

        analysisByAgeResponseDTOS.add(new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "High stress percentage among teenage: ",
                        (CountByAge.NoOfTeeneage > 0) ? (double) (CountByAge.teenageCount * 100) / CountByAge.NoOfTeeneage : 0),
                new ResponseDTO(
                        "High stress percentage among adults: ",
                        (CountByAge.NoOfAdult > 0) ? (double) (CountByAge.adultCount * 100) / CountByAge.NoOfAdult : 0),
                new ResponseDTO(
                        "High stress percentage among seniors: ",
                        (CountByAge.NoOfSenior > 0) ? (double) (CountByAge.seniorCount * 100) / CountByAge.NoOfSenior : 0)
        ));

        analysisByAgeResponseDTOS.add(new AnalysisByAgeResponseDTO(
                new ResponseDTO(
                        "LOW stress percentage among teenage: ",
                        (CountByAge.NoOfTeeneage > 0) ? (double) (CountByAge.teenageCount * 100) / CountByAge.NoOfTeeneage : 0),
                new ResponseDTO(
                        "LOW stress percentage among adults: ",
                        (CountByAge.NoOfAdult > 0) ? (double) (CountByAge.adultCount * 100) / CountByAge.NoOfAdult : 0),
                new ResponseDTO(
                        "LOW stress percentage among seniors: ",
                        (CountByAge.NoOfSenior > 0) ? (double) (CountByAge.seniorCount * 100) / CountByAge.NoOfSenior : 0)
        ));

        return analysisByAgeResponseDTOS;
    }

    private void analyzeHighBloodPressureByAge(PhysicalHealthProxyDTO progressHistoryDTO) {
        if (progressHistoryDTO.getBloodPressure() == BloodPressure.HIGH) {
            count(progressHistoryDTO.getAge());
        }
        ageCount(progressHistoryDTO.getAge());
    }

    private void analyzeLOWBloodPressureByAge(PhysicalHealthProxyDTO progressHistoryDTO) {
        if (progressHistoryDTO.getBloodPressure() == BloodPressure.LOW) {
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
