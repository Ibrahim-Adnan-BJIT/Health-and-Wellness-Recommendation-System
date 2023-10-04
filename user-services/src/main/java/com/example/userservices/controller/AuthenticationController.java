package com.example.userservices.controller;

import com.example.userservices.DTO.request.LogInRequestDTO;
import com.example.userservices.DTO.request.RegisterRequestDTO;
import com.example.userservices.DTO.response.AuthenticationResponseDTO;
import com.example.userservices.DTO.response.LogInResponseDTO;
import com.example.userservices.services.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDTO request){
        AuthenticationResponseDTO responseDTO = authenticationService.register(request);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody LogInRequestDTO request){
        LogInResponseDTO response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
