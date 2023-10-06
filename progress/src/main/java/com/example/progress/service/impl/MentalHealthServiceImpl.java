package com.example.progress.service.impl;

import com.example.progress.dto.response.MentalHealthProgressResponseDTO;
import com.example.progress.entity.MentalHealthProgress;
import com.example.progress.external.enums.LifeSatisfaction;
import com.example.progress.external.enums.StressLevel;
import com.example.progress.repository.MentalHealthProgressRepository;
import com.example.progress.service.MentalHealthService;
import com.example.progress.external.HealthDetails;
import com.example.progress.external.MentalHealth;
import com.example.progress.external.enums.Mode;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentalHealthServiceImpl implements MentalHealthService {
    private final MentalHealthProgressRepository mentalHealthProgressRepository;

    @Override
    public void addMentalHealthProgress(HealthDetails healthDetails) {
        buildMentalHealth(new MentalHealthProgress(), healthDetails);
    }

    private void buildMentalHealth(MentalHealthProgress mentalHealthProgress,
                                   HealthDetails healthDetails) {
        MentalHealth mentalHealth = healthDetails.getMentalHealth();

        mentalHealthProgress.setUserId(healthDetails.getUserId());
        mentalHealthProgress.setAge(healthDetails.getAge());
        mentalHealthProgress.setDepression(mentalHealth.isDepression());
        mentalHealthProgress.setDate(LocalDate.now());
        mentalHealthProgress.setAnxiety(mentalHealth.isAnxiety());
        mentalHealthProgress.setPanicDisorder(mentalHealth.isPanicDisorder());
        mentalHealthProgress.setBipolarDisorder(mentalHealth.isBipolarDisorder());
        mentalHealthProgress.setSchizophrenia(mentalHealth.isSchizophrenia());
        mentalHealthProgress.setMode(mentalHealth.getMode());
        mentalHealthProgress.setStressLevel(mentalHealth.getStressLevel());
        mentalHealthProgress.setLifeSatisfaction(mentalHealth.getLifeSatisfaction());

        mentalHealthProgressRepository.save(mentalHealthProgress);
    }

    private static class MentalHealthTrack {
        int depressionCount = 0;
        int anxietyCount = 0;
        int sadCount = 0;
        int manicCount = 0;
        int moderateStressCount = 0;
        int lowSatisfactionCount = 0;
    }

    @Override
    public MentalHealthProgressResponseDTO analysisMentalHealth(long userId) {
        int days = 7;

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(days);

        List<MentalHealthProgress> mentalHealthProgressList =
                mentalHealthProgressRepository.
                        findLast7DaysByUserId(userId, startDate, today);

        MentalHealthProgressResponseDTO mentalHealthProgressResponseDTO =
                new MentalHealthProgressResponseDTO();

        MentalHealthTrack mentalHealthTrack = new MentalHealthTrack();

        for (MentalHealthProgress progress : mentalHealthProgressList) {

            if (progress.isDepression()) {
                mentalHealthTrack.depressionCount++;
            }
            if (progress.isAnxiety()) {
                mentalHealthTrack.anxietyCount++;
            }
            Mode mode = progress.getMode();
            if (mode == Mode.SAD) {
                mentalHealthTrack.sadCount++;
            } else if (mode == Mode.MANIC) {
                mentalHealthTrack.manicCount++;
            }

            StressLevel stressLevel = progress.getStressLevel();
            LifeSatisfaction lifeSatisfaction = progress.getLifeSatisfaction();
            if (stressLevel == StressLevel.MODERATE) {
                mentalHealthTrack.moderateStressCount++;
            }
            if (lifeSatisfaction == LifeSatisfaction.LOW) {
                mentalHealthTrack.lowSatisfactionCount++;
            }
        }

        StringBuilder messageBuilder = getStringBuilder(mentalHealthTrack);

        mentalHealthProgressResponseDTO.setMentalOverview(messageBuilder.toString());

        return mentalHealthProgressResponseDTO;
    }

    private static StringBuilder getStringBuilder(MentalHealthTrack mentalHealthTrack) {
        StringBuilder messageBuilder = new StringBuilder("In the last 7 days, ");

        if (mentalHealthTrack.depressionCount > 0 || mentalHealthTrack.anxietyCount > 0) {
            messageBuilder.append("you have experienced ");
            if (mentalHealthTrack.depressionCount > 0 && mentalHealthTrack.anxietyCount > 0) {
                messageBuilder.append("both depression and anxiety");
            }
            String mentalState = (mentalHealthTrack.depressionCount > 0) ? "depression" : "anxiety";
            messageBuilder.append(mentalState + ". ");
        } else {
            messageBuilder.append("you have not experienced depression or anxiety. ");
        }

        if (mentalHealthTrack.sadCount > 0 || mentalHealthTrack.manicCount > 0) {
            messageBuilder.append("Your emotional mode has been ");
            if (mentalHealthTrack.sadCount > 0) {
                messageBuilder.append("sad");
                if (mentalHealthTrack.manicCount > 0) {
                    messageBuilder.append(" and ");
                }
            }
            if (mentalHealthTrack.manicCount > 0) {
                messageBuilder.append("manic");
            }
            messageBuilder.append(" during this period. ");
        } else {
            messageBuilder.append("your emotional mode has been stable. ");
        }

        if (mentalHealthTrack.moderateStressCount > 0) {
            messageBuilder.append("You have experienced moderate stress levels. ");
        }
        if (mentalHealthTrack.lowSatisfactionCount > 0) {
            messageBuilder.append("Your life satisfaction has been low. ");
        }
        return messageBuilder;
    }
}
