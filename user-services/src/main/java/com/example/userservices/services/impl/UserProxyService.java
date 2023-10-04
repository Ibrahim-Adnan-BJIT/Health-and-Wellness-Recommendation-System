package com.example.userservices.services.impl;

import com.example.userservices.DTO.response.HealthDetailsDto;
import com.example.userservices.exception.CustomeException;
import com.example.userservices.model.HealthDetails;
import com.example.userservices.repository.HealthRepository;
import com.example.userservices.services.IUserProxyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserProxyService implements IUserProxyService {
    private final HealthRepository healthRepository;

    public UserProxyService(HealthRepository healthRepository) {
        this.healthRepository = healthRepository;
    }

    public HealthDetails getUserHealthDetails(long userId) {
        // return health details
        return healthRepository.findByUserId(userId).orElseThrow(() ->
                new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create your profile. Please create!!"));
    }
}
