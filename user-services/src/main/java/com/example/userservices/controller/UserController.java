package com.example.userservices.controller;

import com.example.userservices.DTO.request.HealthDetailsDTO;
import com.example.userservices.DTO.request.UserRequestDTO;
import com.example.userservices.DTO.response.HealthDetailsDto;
import com.example.userservices.DTO.response.UserProfileResponseDTO;
import com.example.userservices.model.HealthDetails;
import com.example.userservices.response.ResponseHandler;
import com.example.userservices.services.IAuthenticationService;
import com.example.userservices.services.IUserCommandService;
import com.example.userservices.services.IUserQueryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final IAuthenticationService authenticationService;
    private final IUserCommandService userCommandService;
    private final IUserQueryService userQueryService;

    // Register For your profile and Health information
    @PostMapping("/create")
    public ResponseEntity<Object> createUserProfileHealthInformation(@Valid @RequestBody UserRequestDTO userRequestDTO){
        long userId = authenticationService.getAuthenticatedUser();
        userCommandService.createUserDetails(userId, userRequestDTO);
        return ResponseHandler.generateResponse("Create User Profile Successfully", HttpStatus.CREATED);
    }

    // Update user Health information
    @PutMapping("/update")
    public ResponseEntity<Object> updateUserHealthInformation(@Valid@RequestBody HealthDetailsDTO healthDetailsDTO){
        long userId = authenticationService.getAuthenticatedUser();
        userCommandService.updateHealthInformation(userId, healthDetailsDTO);
        return ResponseHandler.generateResponse("Update Health Information Successfully", HttpStatus.OK);
    }


    // Get User Health Information
    @GetMapping("/health-data")
    public ResponseEntity<Object> getHealthInformation(){
        long userId = authenticationService.getAuthenticatedUser();
        HealthDetailsDto response = userQueryService.getUserHealthDetails(userId);
        return ResponseHandler.generateResponse("Fetch Health Information Successfully", HttpStatus.OK, response);
    }

    // Get user Profile information
    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(){
        long userId = authenticationService.getAuthenticatedUser();
        UserProfileResponseDTO response = userQueryService.getUserProfile(userId);
        return ResponseHandler.generateResponse("Fetch User Profile Data Successfully", HttpStatus.OK, response);
    }
}
