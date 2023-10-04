package com.example.userservices.controller;

import com.example.userservices.DTO.response.HealthDetailsDto;
import com.example.userservices.model.HealthDetails;
import com.example.userservices.response.ResponseHandler;
import com.example.userservices.services.IAuthenticationService;
import com.example.userservices.services.IUserProxyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/proxyUser")
public class ProxyController {
    private final IUserProxyService userProxyService;

    public ProxyController(IUserProxyService userProxyService) {
        this.userProxyService = userProxyService;
    }

    // Get User Health Information for Internal API Call
    @GetMapping("/health-data/{userId}")
    public HealthDetails getHealthInformation(@PathVariable long userId){
        return userProxyService.getUserHealthDetails(userId);
    }
}
