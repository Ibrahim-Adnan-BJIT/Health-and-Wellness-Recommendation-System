package com.example.progress.service.impl;

import com.example.progress.dto.response.MentalHealthProgressResponseDTO;
import com.example.progress.dto.response.MentalHealthProxyDTO;
import com.example.progress.dto.response.PhysicalHealthProxyDTO;
import com.example.progress.entity.MentalHealthProgress;
import com.example.progress.entity.PhysicalHealthProgress;
import com.example.progress.repository.MentalHealthProgressRepository;
import com.example.progress.repository.PhysicalHealthProgressRepository;
import com.example.progress.service.ProgressProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressProxyServiceImpl implements ProgressProxyService {
    private final MentalHealthProgressRepository mentalHealthProgressRepository;
    private final PhysicalHealthProgressRepository physicalHealthProgressRepository;

    @Override
    public List<MentalHealthProxyDTO> getMentalHealthProgress() {
        List<MentalHealthProgress> mentalHealthProgress = mentalHealthProgressRepository.findAll();
        return mentalHealthProgress.stream()
                .map(this::mapTOMentalDTO).toList();
    }

    private MentalHealthProxyDTO mapTOMentalDTO(MentalHealthProgress mentalHealthProgress) {
        return MentalHealthProxyDTO.builder()
                .age(mentalHealthProgress.getAge())
                .depression(mentalHealthProgress.isDepression())
                .anxiety(mentalHealthProgress.isAnxiety())
                .stressLevel(mentalHealthProgress.getStressLevel())
                .build();
    }

    @Override
    public List<PhysicalHealthProxyDTO> getPhysicalHealthProgress() {
        List<PhysicalHealthProgress> physicalHealthProgresses = physicalHealthProgressRepository.findAll();
        return physicalHealthProgresses.stream()
                .map(this::mapTOPhysicalDTO).toList();
    }

    private PhysicalHealthProxyDTO mapTOPhysicalDTO(PhysicalHealthProgress physicalHealthProgress) {
        return PhysicalHealthProxyDTO.builder()
                .age(physicalHealthProgress.getAge())
                .smoke(physicalHealthProgress.isSmoke())
                .bloodPressure(physicalHealthProgress.getBloodPressure())
                .sleepIssue(physicalHealthProgress.getSleepIssue())
                .build();
    }
}
