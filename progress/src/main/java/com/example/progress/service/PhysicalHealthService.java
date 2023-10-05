package com.example.progress.service;


import com.example.progress.dto.response.PhysicalHealthProgressResponseDTO;
import com.example.progress.external.HealthDetails;

public interface PhysicalHealthService {
    public void addPhysicalHealthProgress(HealthDetails healthDetails);

    public PhysicalHealthProgressResponseDTO analysisPhysicalHealth(long userId);
}
