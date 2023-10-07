package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.PreferenceDto;
import com.example.notificationservice.entity.Preference;
import com.example.notificationservice.exception.InvalidPreference;
import com.example.notificationservice.repository.PreferenceRepo;
import com.example.notificationservice.service.PreferenceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    private PreferenceRepo preferenceRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PreferenceDto createPreference(Preference preference) throws InvalidPreference {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        preference.setUserId(id);
        if(preference.getTopics()==null || preference.getName()==null || preference.getEmail()==null)
        {
            throw new InvalidPreference("Please Fill up fields properly");
        }
       preferenceRepo.save(preference);
       return modelMapper.map(preference,PreferenceDto.class);
    }
}
