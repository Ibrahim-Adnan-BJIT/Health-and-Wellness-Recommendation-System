package com.example.recommendationservice.services;

import com.example.recommendationservice.external.HealthDetails;

public interface IDataProcessService {
    public void importHealthDetails(HealthDetails healthDetails);
}
