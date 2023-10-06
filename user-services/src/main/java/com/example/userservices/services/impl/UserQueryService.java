package com.example.userservices.services.impl;

import com.example.userservices.DTO.response.MentalHealthDTO;
import com.example.userservices.DTO.response.PhysicalHealthDTO;
import com.example.userservices.DTO.response.DailyScheduleDTO;
import com.example.userservices.DTO.response.HealthDetailsDto;
import com.example.userservices.DTO.response.UserInformation;
import com.example.userservices.DTO.response.UserProfileResponseDTO;
import com.example.userservices.exception.AuthenticationException;
import com.example.userservices.exception.CustomeException;
import com.example.userservices.feignclient.SecurityServiceClient;
import com.example.userservices.feignclient.handleException.FeignCustomException;
import com.example.userservices.model.*;
import com.example.userservices.repository.HealthRepository;
import com.example.userservices.repository.UserRepository;
import com.example.userservices.services.IUserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserQueryService implements IUserQueryService {
    private final UserRepository userRepository;
    private final HealthRepository healthRepository;
    private final SecurityServiceClient securityServiceClient;

    public UserQueryService(UserRepository userRepository, HealthRepository healthRepository, SecurityServiceClient securityServiceClient) {
        this.userRepository = userRepository;
        this.healthRepository = healthRepository;
        this.securityServiceClient = securityServiceClient;
    }

    // Fetch user profile information
    @Override
    public UserProfileResponseDTO getUserProfile(long userId) {
        // Fetches user health data and check if existing
        HealthDetails healthDetails = healthRepository.findByUserId(userId).orElseThrow(() ->
                new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create your profile. Please create!!"));

        // Fetch user profile information
        UserProfile userProfile = userRepository.findByUserId(userId);
        // Fetch User email and name from Auth service
        UserInformation userInformation;
        try {
            userInformation = securityServiceClient.getUserInformation(userId);
        } catch (FeignCustomException ex) {
            log.error("Failed to get user information");
            throw new AuthenticationException(ex.getStatusCode(), ex.getErrorDetails().getMessage());
        }

        // Convert model to DTO and return data
        return mapToDto(userProfile, userInformation, healthDetails);
    }

    // Fetches user health information
    public HealthDetailsDto getUserHealthDetails(long userId) {
        HealthDetails healthDetails = healthRepository.findByUserId(userId).orElseThrow(() ->
                new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create your profile. Please create!!"));

        // Build HealthDetailsDTO
        return buildHealthDetailsDTO(healthDetails);
    }

    // Map HealthDetails entity to DTO
    private HealthDetailsDto buildHealthDetailsDTO(HealthDetails healthDetails) {
        return HealthDetailsDto.builder()
                .userId(healthDetails.getUserId())
                .age(healthDetails.getAge())
                .weight(healthDetails.getWeight())
                .height(healthDetails.getHeight())
                .bmi(healthDetails.getBmi())
                .bmr(healthDetails.getBmr())
                .bloodGroup(healthDetails.getBloodGroup().toString())
                .goalType(healthDetails.getGoalType().toString())
                .activityLevel(healthDetails.getActivityLevel().toString())
                .gender(healthDetails.getGender().toString())
                .dailySchedule(convertToDailyScheduleDTO(healthDetails.getDailySchedule()))
                .mentalHealth(convertToMentalHealthDTO(healthDetails.getMentalHealth()))
                .physicalHealth(convertToPhysicalHealthDTO(healthDetails.getPhysicalHealth()))
                .build();
    }

    // Define methods to convert nested entities to DTOs
    private DailyScheduleDTO convertToDailyScheduleDTO(DailySchedule dailySchedule) {
        return (dailySchedule != null) ? DailyScheduleDTO.builder()
                .wakeTime(dailySchedule.getWakeTime())
                .bedTime(dailySchedule.getBedTime())
                .build() : null;
    }

    // Map MentalHealth entity to DTO
    private MentalHealthDTO convertToMentalHealthDTO(MentalHealth mentalHealth) {
        return (mentalHealth != null) ? MentalHealthDTO.builder()
                .depression(mentalHealth.isDepression())
                .anxiety(mentalHealth.isAnxiety())
                .panicDisorder(mentalHealth.isPanicDisorder())
                .bipolarDisorder(mentalHealth.isBipolarDisorder())
                .schizophrenia(mentalHealth.isSchizophrenia())
                .mode(mentalHealth.getMode().toString())
                .stressLevel(mentalHealth.getStressLevel().toString())
                .lifeSatisfaction(mentalHealth.getLifeSatisfaction().toString())
                .build() : null;
    }

    // Map PhysicalHealth entity to DTO
    private PhysicalHealthDTO convertToPhysicalHealthDTO(PhysicalHealth physicalHealth) {
        return (physicalHealth != null) ? PhysicalHealthDTO.builder()
                .smoke(physicalHealth.isSmoke())
                .diabetesLevel(physicalHealth.getDiabetesLevel().toString())
                .bloodPressure(physicalHealth.getBloodPressure().toString())
                .motivationLevel(physicalHealth.getMotivationLevel().toString())
                .alcoholConsumption(physicalHealth.getAlcoholConsumption().toString())
                .caffeineConsumption(physicalHealth.getCaffeineConsumption().toString())
                .sleepIssue(physicalHealth.getSleepIssue().toString())
                .build() : null;
    }


    // Convert User Profile Model to DTO
    private UserProfileResponseDTO mapToDto(UserProfile userProfile,
                                            UserInformation userInformation,
                                            HealthDetails healthDetails) {

        return UserProfileResponseDTO
                .builder()
                .id(userProfile.getId())
                .username(userInformation.getUsername())
                .email(userInformation.getEmail())
                .address(userProfile.getAddress())
                .age(healthDetails.getAge())
                .gender(healthDetails.getGender().toString())
                .mobile(userProfile.getMobile())
                .nationality(userProfile.getNationality())
                .homeDistrict(userProfile.getHomeDistrict())
                .weight(healthDetails.getWeight())
                .height(healthDetails.getHeight())
                .bmi(healthDetails.getBmi())
                .bmr(healthDetails.getBmr())
                .bloodGroup(healthDetails.getBloodGroup().toString())
                .build();
    }
}
