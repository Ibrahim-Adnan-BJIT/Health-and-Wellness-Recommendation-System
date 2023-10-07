package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.EventDto;
import com.example.notificationservice.entity.Event;
import com.example.notificationservice.entity.Preference;
import com.example.notificationservice.exception.EventNotFoundException;
import com.example.notificationservice.repository.EventRepo;
import com.example.notificationservice.repository.PreferenceRepo;

import com.example.notificationservice.service.EmailService;
import com.example.notificationservice.service.EventService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private EventRepo eventRepo;


    private ModelMapper modelMapper;

    private PreferenceRepo preferenceRepo;

    private EmailService emailService;
    @Override
    public EventDto createEvent(Event event) throws EventNotFoundException {

        if(event.getTopics()==null || event.getName()==null || event.getSpeaker()==null || event.getVenue()==null)
        {
            throw new EventNotFoundException("Please Fill up fields carefully");
        }
        List<Preference>preferences=preferenceRepo.findByTopics(event.getTopics());

       for(Preference p1: preferences)
       {
          emailService.sendEmail(p1.getEmail(), String.valueOf(p1.getTopics()),"Hello , "+p1.getName()+" Hope you are " +
                  " doing well .we are happy to invite you in our event "+event.getTopics()+". You can get all the details of this event on our website  . Event is on next week and the venue is "+event.getVenue()+" and In that event we have a special Guest" +
                  " called "+event.getSpeaker()+" so dont miss this opportunity .See you on the event till then Adios...." +
                  " Regards " +
                  " Admin  "+event.getName());
       }

       eventRepo.save(event);
       return modelMapper.map(event,EventDto.class);
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events=eventRepo.findAll();
        return events.stream().map((todo) -> modelMapper.map(todo, EventDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventByName(String name) throws EventNotFoundException{
        Optional<Event> event= Optional.ofNullable(eventRepo.findByName(name));
        if(event.isEmpty())
            throw new EventNotFoundException("Please enter event name carefully");
        return modelMapper.map(event.get(),EventDto.class);
    }


}
