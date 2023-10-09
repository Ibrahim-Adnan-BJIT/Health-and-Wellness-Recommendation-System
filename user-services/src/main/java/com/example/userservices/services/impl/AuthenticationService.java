package com.example.userservices.services.impl;

import com.example.userservices.DTO.request.LogInRequestDTO;
import com.example.userservices.DTO.request.RegisterRequestDTO;
import com.example.userservices.DTO.response.AuthenticationResponseDTO;
import com.example.userservices.DTO.response.LogInResponseDTO;
import com.example.userservices.exception.AuthenticationException;
import com.example.userservices.feignclient.SecurityServiceClient;
import com.example.userservices.feignclient.handleException.FeignCustomException;
import com.example.userservices.services.IAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private final SecurityServiceClient securityServiceClient;

    public AuthenticationService(SecurityServiceClient securityServiceClient) {
        this.securityServiceClient = securityServiceClient;
    }

    // For Register
    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {

        AuthenticationResponseDTO register;
        try {
            log.info("Calling feing client for registration");
            register = securityServiceClient.register(request);
        } catch (FeignCustomException ex) {
            log.error("An error occurred during account creation", ex);
            throw new AuthenticationException(ex.getStatusCode(), ex.getErrorDetails().getMessage());
        }
        return register;
    }

    // For Log in
    @Override
    public LogInResponseDTO authenticate(LogInRequestDTO request) {
        LogInResponseDTO response;
        try{
            log.info("Calling feing client for login");
            response = securityServiceClient.authenticate(request);
        } catch (FeignCustomException ex) {
            log.error("An error occurred during account log in", ex);
            throw new AuthenticationException(ex.getStatusCode(), ex.getErrorDetails().getMessage());
        }
        return response;
    }

    // Get userId from Principal
    public long getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}
