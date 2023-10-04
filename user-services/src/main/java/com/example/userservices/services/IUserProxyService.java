package com.example.userservices.services;

import com.example.userservices.model.HealthDetails;

public interface IUserProxyService {
    public HealthDetails getUserHealthDetails(long userId);
}
