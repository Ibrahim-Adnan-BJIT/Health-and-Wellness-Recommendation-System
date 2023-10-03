package com.example.progress.service.impl;

import com.example.progress.dto.response.PhysicalHealthProgressResponseDTO;
import com.example.progress.entity.PhysicalHealthProgress;
import com.example.progress.external.PhysicalHealth;
import com.example.progress.external.enums.BloodPressure;
import com.example.progress.external.enums.DiabetesLevel;
import com.example.progress.external.enums.MotivationLevel;
import com.example.progress.repository.PhysicalHealthProgressRepository;
import com.example.progress.service.PhysicalHealthService;
import com.example.progress.external.HealthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhysicalHealthServiceImpl implements PhysicalHealthService {
    private final PhysicalHealthProgressRepository physicalHealthProgressRepository;

    @Override
    public void addPhysicalHealthProgress(HealthDetails healthDetails) {

        buildPhysicalHealth(new PhysicalHealthProgress(), healthDetails);
    }

    private void buildPhysicalHealth(PhysicalHealthProgress physicalHealthProgress, HealthDetails healthDetails) {
        PhysicalHealth physicalHealth = healthDetails.getPhysicalHealth();

        physicalHealthProgress.setUserId(healthDetails.getUserId());
        physicalHealthProgress.setSmoke(physicalHealth.isSmoke());
        physicalHealthProgress.setDiabetesLevel(physicalHealth.getDiabetesLevel());
        physicalHealthProgress.setBloodPressure(physicalHealth.getBloodPressure());
        physicalHealthProgress.setMotivationLevel(physicalHealth.getMotivationLevel());
        physicalHealthProgress.setAlcoholConsumption(physicalHealth.getAlcoholConsumption());
        physicalHealthProgress.setCaffeineConsumption(physicalHealth.getCaffeineConsumption());
        physicalHealthProgress.setSleepIssue(physicalHealth.getSleepIssue());
        physicalHealthProgress.setDate(LocalDate.now());

        physicalHealthProgressRepository.save(physicalHealthProgress);
    }

    @Override
    public PhysicalHealthProgressResponseDTO analysisPhysicalHealth(long userId) {
        int days = 7;

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(days - 1);
        List<PhysicalHealthProgress> progressList = physicalHealthProgressRepository
        		.findLast7DaysByUserId(userId, startDate, today);

        PhysicalHealthProgressResponseDTO responseDTO = calculateInsights(progressList);

        return responseDTO;
    }

    private PhysicalHealthProgressResponseDTO calculateInsights(List<PhysicalHealthProgress> progressList) {
        PhysicalHealthProgressResponseDTO responseDTO = new PhysicalHealthProgressResponseDTO();

        int totalRecords = progressList.size();
        String bloodPressureMessage = calculateBloodPressureMessage(progressList);
        String diabetesLevelMessage = calculateDiabetesMessage(progressList);
        String motivationMessage = generateMotivationMessage(progressList);

        responseDTO.setTotalRecords("Total no of records " + Integer.toString(totalRecords));
        responseDTO.setBloodPressureMessage(bloodPressureMessage);
        responseDTO.setDiabetesLevelMessage(diabetesLevelMessage);
        responseDTO.setMotivationMessage(motivationMessage);

        return responseDTO;
    }

    private String calculateBloodPressureMessage(List<PhysicalHealthProgress> progressList) {
        int highRate = 0;
        int lowRate = 0;

        for (PhysicalHealthProgress progress : progressList) {
            if (progress.getBloodPressure() == BloodPressure.HIGH) {
                highRate++;
            } else if (progress.getBloodPressure() == BloodPressure.LOW) {
                lowRate++;
            }
        }

        return generateBloodPressureMessage(highRate, lowRate, progressList.size());
    }

    private String generateBloodPressureMessage(int highRate, int lowRate, int noOfRecoreds) {
        StringBuilder message = new StringBuilder("Blood Pressure Analysis: ");


        if (highRate > 0) {
            double highBloodPressurePercentage = (double) (highRate * 100.0)/ noOfRecoreds ;
            String formattedPercentage = String.format("%.4f", highBloodPressurePercentage);
            
            message.append("High Blood Pressure detected. Percentage " 
            + formattedPercentage + " in total records. ");
        }

        if (lowRate > 0) {
            double lowBloodPressurePercentage = (double) (lowRate * 100.0) / noOfRecoreds;
            
            String formattedPercentage = String.format("%.4f", lowBloodPressurePercentage);
            message.append("Low Blood Pressure detected. Percentage " 
            + formattedPercentage + " in total records. ");
        }

        if (highRate == 0 && lowRate == 0) {
            message.append("No significant blood pressure issues detected.");
        }

        return message.toString();
    }

    private String calculateDiabetesMessage(List<PhysicalHealthProgress> progressList) {
        int type1Count = 0;
        int type2Count = 0;

        for (PhysicalHealthProgress progress : progressList) {
            if (progress.getDiabetesLevel() == DiabetesLevel.TYPE_1) {
                type1Count++;
            } else if (progress.getDiabetesLevel() == DiabetesLevel.TYPE_2) {
                type2Count++;
            }
        }

        return generateDiabetesMessage(type1Count, type2Count, progressList.size());
    }

    private String generateDiabetesMessage(int type1Count, int type2Count, int noOfRecords) {
        StringBuilder message = new StringBuilder("Diabetes Analysis: ");

        if (type1Count > 0) {
            double type1Percentage = (double) type1Count / noOfRecords * 100.0;
            message.append("TYPE 1 Diabetes detected. Percentage ")
                    .append(type1Percentage)
                    .append(" in total records. ");
        }

        if (type2Count > 0) {
            double type2Percentage = (double) type2Count / noOfRecords * 100.0;
            message.append("TYPE 2 Diabetes detected. Percentage ")
                    .append(type2Percentage)
                    .append(" in total records.");
        }

        if (type1Count == 0 && type2Count == 0) {
            message.append("No significant diabetes issues detected.");
        }

        return message.toString();
    }


    private String generateMotivationMessage(List<PhysicalHealthProgress> progressList) {
        long count = progressList.stream().filter(progress -> progress.getMotivationLevel() == MotivationLevel.MODERATE || progress.getMotivationLevel() == MotivationLevel.HIGH).count();
        if ((long) progressList.size() / 2 < count) {
            return "Motivation Analysis: High motivation levels detected more than 50% in total records.";
        }
        return "Motivation Analysis: No significant high motivation levels detected.";
    }
}
