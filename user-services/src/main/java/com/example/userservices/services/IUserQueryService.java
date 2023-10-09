package com.example.userservices.services;

import com.example.userservices.DTO.response.HealthDetailsDto;
import com.example.userservices.DTO.response.UserProfileResponseDTO;

public interface IUserQueryService {
    public UserProfileResponseDTO getUserProfile(long userId);
    public HealthDetailsDto getUserHealthDetails(long userId);
}
