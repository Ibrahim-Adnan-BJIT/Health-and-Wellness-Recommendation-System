package com.example.progress.service;

import com.example.progress.dto.response.MentalHealthProxyDTO;
import com.example.progress.dto.response.PhysicalHealthProxyDTO;
import com.example.progress.entity.MentalHealthProgress;
import com.example.progress.entity.PhysicalHealthProgress;

import java.util.List;

public interface ProgressProxyService {
    public List<MentalHealthProxyDTO> getMentalHealthProgress();

    public List<PhysicalHealthProxyDTO> getPhysicalHealthProgress();

}
