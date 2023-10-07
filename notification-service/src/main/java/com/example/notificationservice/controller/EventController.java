package com.example.notificationservice.controller;

import com.example.notificationservice.dto.EventDto;
import com.example.notificationservice.entity.Event;
import com.example.notificationservice.service.EventService;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<EventDto> createEvent(@RequestBody Event event)
    {
        EventDto eventDto=eventService.createEvent(event);
        return new ResponseEntity<>(eventDto, HttpStatus.CREATED);
    }

    @GetMapping("/getAllEvent")
    public  ResponseEntity<List<EventDto>> getAllEvent()
    {
        List<EventDto>eventDtos=eventService.getAllEvents();
        return new ResponseEntity<>(eventDtos,HttpStatus.FOUND);
    }

    @GetMapping("/getByName/{str}")
    public ResponseEntity<EventDto> getByName(@PathVariable String str)
    {
        EventDto eventDto=eventService.getEventByName(str);
        return new ResponseEntity<>(eventDto,HttpStatus.FOUND);
    }
}
