package com.example.securityservice.controller;


import com.example.securityservice.DTO.response.AuthenticationResponseDTO;
import com.example.securityservice.DTO.request.LogInRequestDTO;
import com.example.securityservice.DTO.response.LogInResponseDTO;
import com.example.securityservice.DTO.request.RegisterRequestDTO;
import com.example.securityservice.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
@AllArgsConstructor
public class AuthenticationController {

    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request){
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public LogInResponseDTO authenticate(@RequestBody LogInRequestDTO request){
        return authenticationService.authenticate(request);
    }
}
