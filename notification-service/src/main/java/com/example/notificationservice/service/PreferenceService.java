package com.example.notificationservice.service;

import com.example.notificationservice.dto.PreferenceDto;
import com.example.notificationservice.entity.Preference;
import com.example.notificationservice.exception.InvalidPreference;

public interface PreferenceService {

   PreferenceDto createPreference(Preference preference)throws InvalidPreference;
}
