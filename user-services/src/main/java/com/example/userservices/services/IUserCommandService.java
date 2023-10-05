package com.example.userservices.services;

import com.example.userservices.DTO.request.HealthDetailsDTO;
import com.example.userservices.DTO.request.UserRequestDTO;
import com.example.userservices.DTO.response.ProxyResponse;

public interface IUserCommandService {
    public void createUserDetails(long userId, UserRequestDTO userRequestDTO);
    public void updateHealthInformation(long userId, HealthDetailsDTO healthDetailsDTO);
}
