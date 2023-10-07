package com.example.notificationservice.repository;

import com.example.notificationservice.entity.Preference;
import com.example.notificationservice.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepo extends JpaRepository<Preference,Long> {
    List<Preference> findByTopics(Topics topics);

}
