package com.example.userservices.services;

import com.example.userservices.DTO.request.LogInRequestDTO;
import com.example.userservices.DTO.request.RegisterRequestDTO;
import com.example.userservices.DTO.response.AuthenticationResponseDTO;
import com.example.userservices.DTO.response.LogInResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    public AuthenticationResponseDTO register(RegisterRequestDTO request);
    public LogInResponseDTO authenticate(LogInRequestDTO request);
    public long getAuthenticatedUser();
}
