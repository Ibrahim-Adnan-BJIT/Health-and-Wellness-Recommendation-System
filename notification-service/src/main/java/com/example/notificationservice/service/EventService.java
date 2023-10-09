package com.example.notificationservice.service;

import com.example.notificationservice.dto.EventDto;
import com.example.notificationservice.entity.Event;
import com.example.notificationservice.exception.EventNotFoundException;

import java.util.List;

public interface EventService {
    EventDto createEvent(Event event) throws EventNotFoundException;
    List<EventDto> getAllEvents();
    EventDto getEventByName(String name) throws EventNotFoundException;
}
