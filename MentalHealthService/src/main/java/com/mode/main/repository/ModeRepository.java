package com.mode.main.repository;

import com.mode.main.entity.ModeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeRepository extends JpaRepository<ModeEntity, Long> {

    List<ModeEntity> findByUserId(Long userId);

}
