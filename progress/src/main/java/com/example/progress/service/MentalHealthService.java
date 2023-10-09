package com.example.progress.service;

import com.example.progress.dto.response.MentalHealthProgressResponseDTO;
import com.example.progress.external.HealthDetails;

public interface MentalHealthService {
    public void addMentalHealthProgress(HealthDetails healthDetails);

    public MentalHealthProgressResponseDTO analysisMentalHealth(long userId);
}
