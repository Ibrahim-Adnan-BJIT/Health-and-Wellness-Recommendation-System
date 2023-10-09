package com.example.notificationservice.controller;

import com.example.notificationservice.dto.PreferenceDto;
import com.example.notificationservice.entity.Preference;
import com.example.notificationservice.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/preference")
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;

    @PostMapping("/create")
    public ResponseEntity<PreferenceDto> createPreference(@RequestBody Preference preference)
    {
        PreferenceDto preferenceDto=preferenceService.createPreference(preference);
        return new ResponseEntity<>(preferenceDto, HttpStatus.CREATED);
    }
}
