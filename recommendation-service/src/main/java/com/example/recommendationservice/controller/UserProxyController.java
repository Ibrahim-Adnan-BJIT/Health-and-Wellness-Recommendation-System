package com.example.recommendationservice.controller;

import com.example.recommendationservice.DTO.response.ProxyResponse;
import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.services.IDataProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/user")
public class UserProxyController {

    private final IDataProcessService healthDetailsService;

    public UserProxyController(IDataProcessService healthDetailsService) {
        this.healthDetailsService = healthDetailsService;
    }

    @PostMapping("/health-data")
    public ProxyResponse importUserHealthData(@RequestBody HealthDetails healthDetails){
        healthDetailsService.importHealthDetails(healthDetails);
        return new ProxyResponse("Data received successfully");
    }
}
