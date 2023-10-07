package com.example.notificationservice.repository;

import com.example.notificationservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Long> {

    Event findByName(String name);
}
