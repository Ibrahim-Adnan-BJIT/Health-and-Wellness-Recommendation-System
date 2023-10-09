package com.example.progress.service.impl;

import com.example.progress.dto.response.PhysicalHealthProgressResponseDTO;
import com.example.progress.entity.PhysicalHealthProgress;
import com.example.progress.external.PhysicalHealth;
import com.example.progress.external.enums.BloodPressure;
import com.example.progress.external.enums.DiabetesLevel;
import com.example.progress.external.enums.MotivationLevel;
import com.example.progress.external.enums.SleepIssue;
import com.example.progress.repository.PhysicalHealthProgressRepository;
import com.example.progress.service.PhysicalHealthService;
import com.example.progress.external.HealthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
        physicalHealthProgress.setAge(healthDetails.getAge());
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

        return calculateInsights(progressList);
    }

    private PhysicalHealthProgressResponseDTO calculateInsights(List<PhysicalHealthProgress> progressList) {
        PhysicalHealthProgressResponseDTO responseDTO = new PhysicalHealthProgressResponseDTO();

        int totalRecords = progressList.size();

        responseDTO.setTotalRecords("Total no of records " + Integer.toString(totalRecords));
        responseDTO.setBloodPressureMessage(analysisBloodPressure(progressList));
        responseDTO.setDiabetesLevelMessage(analysisDiabetes(progressList));
        responseDTO.setMotivationMessage(generateMotivationMessage(progressList));
        responseDTO.setSleepMessage(analysisSleepIssue(progressList));

        return responseDTO;
    }

    private String analysisBloodPressure(List<PhysicalHealthProgress> progressList) {
        Map<BloodPressure, Integer> bloodPressureCounts = new EnumMap<>(BloodPressure.class);

        for (PhysicalHealthProgress progress : progressList) {
            BloodPressure bloodPressure = progress.getBloodPressure();
            bloodPressureCounts.put(bloodPressure, bloodPressureCounts.getOrDefault(bloodPressure, 0) + 1);
        }

        return generateBloodPressureMessage(bloodPressureCounts, progressList.size());
    }

    private String generateBloodPressureMessage(Map<BloodPressure, Integer> bloodPressureCounts, int noOfRecords) {
        StringBuilder message = new StringBuilder("Blood Pressure Analysis: ");

        for (BloodPressure pressure : BloodPressure.values()) {
            if (bloodPressureCounts.containsKey(pressure)) {
                int count = bloodPressureCounts.get(pressure);
                double percentage = (double) count / noOfRecords * 100.0;
                message.append(pressure.name()).append(" detected ")
                        .append(String.format("%.4f", percentage))
                        .append(" percentage in total records. ");
            }
        }

        boolean noIssuesDetected = bloodPressureCounts.isEmpty();
        if (noIssuesDetected) {
            message.append("No significant blood pressure issues detected.");
        }

        return message.toString();
    }

    private String analysisDiabetes(List<PhysicalHealthProgress> progressList) {
        Map<DiabetesLevel, Integer> diabetesCounts = new EnumMap<>(DiabetesLevel.class);

        for (PhysicalHealthProgress progress : progressList) {
            DiabetesLevel diabetesLevel = progress.getDiabetesLevel();
            diabetesCounts.put(diabetesLevel, diabetesCounts.getOrDefault(diabetesLevel, 0) + 1);
        }

        return generateDiabetesMessage(diabetesCounts, progressList.size());
    }

    private String generateDiabetesMessage(Map<DiabetesLevel, Integer> diabetesCounts, int noOfRecords) {
        StringBuilder message = new StringBuilder("Diabetes Analysis: ");

        for (DiabetesLevel level : DiabetesLevel.values()) {
            if (diabetesCounts.containsKey(level)) {
                int count = diabetesCounts.get(level);
                double percentage = (double) count / noOfRecords * 100.0;
                message.append(level.name()).append(" detected. Percentage ")
                        .append(String.format("%.4f", percentage))
                        .append(" in total records. ");
            }
        }

        boolean noIssuesDetected = diabetesCounts.isEmpty();
        if (noIssuesDetected) {
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

    private String analysisSleepIssue(List<PhysicalHealthProgress> progressList) {
        Map<SleepIssue, Integer> sleepIssueCounts = new EnumMap<>(SleepIssue.class);

        for (PhysicalHealthProgress progress : progressList) {
            SleepIssue sleepIssue = progress.getSleepIssue();
            sleepIssueCounts.put(sleepIssue, sleepIssueCounts.getOrDefault(sleepIssue, 0) + 1);
        }

        return generateSleepIssueMessage(sleepIssueCounts, progressList.size());
    }

    private String generateSleepIssueMessage(Map<SleepIssue, Integer> sleepIssueCounts, int noOfRecords) {
        StringBuilder message = new StringBuilder("Sleep Issue Analysis: ");

        for (SleepIssue sleepIssue : SleepIssue.values()) {
            if (sleepIssueCounts.containsKey(sleepIssue)) {
                int count = sleepIssueCounts.get(sleepIssue);
                double percentage = (double) count / noOfRecords * 100.0;
                message.append(sleepIssue.name()).append(" detected. Percentage ")
                        .append(percentage).append(" in total records. ");
            }
        }

        boolean noIssuesDetected = sleepIssueCounts.isEmpty();
        if (noIssuesDetected) {
            message.append("No significant sleep issues detected.");
        }

        return message.toString();
    }
}
