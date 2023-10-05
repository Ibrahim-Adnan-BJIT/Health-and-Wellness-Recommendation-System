package com.mode.main.service;

import com.mode.main.entity.ModeEntity;
import com.mode.main.repository.ModeRepository;
import com.mode.main.sourcemodel.HealthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ModeTrackingService {
    @Autowired
    private final ModeRepository modeRepository;

    @Autowired
    public ModeTrackingService(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    public ModeEntity trackMode(HealthDetails mode) {
        ModeEntity modeEntity = new ModeEntity();
        modeEntity.setUserId(mode.getUserId());
        modeEntity.setMode(mode.getMentalHealth().getMode());
        modeEntity.setDate(LocalDate.now());
        return modeRepository.save(modeEntity);
    }

    public List<ModeEntity> getUserModeHistory(Long userId) {
        List<ModeEntity> userModeHistory = modeRepository.findByUserId(userId);
        return userModeHistory;
    }

}
