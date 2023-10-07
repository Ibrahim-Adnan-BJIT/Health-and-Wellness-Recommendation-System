package com.example.userservices.services.impl;

import com.example.userservices.DTO.request.*;
import com.example.userservices.exception.CustomeException;
import com.example.userservices.feignclient.RecommendationsClient;
import com.example.userservices.model.*;
import com.example.userservices.repository.HealthRepository;
import com.example.userservices.repository.UserRepository;
import com.example.userservices.services.IUserCommandService;
import com.example.userservices.utils.Constants;
import com.example.userservices.utils.EnumValidation;
import com.example.userservices.webclient.IMentalHealthServiceClient;
import com.example.userservices.webclient.INutritionServiceClient;
import com.example.userservices.webclient.IProgressServiceClient;
import com.example.userservices.webclient.IRecommendationServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@Slf4j
public class UserCommandService implements IUserCommandService {
    private final UserRepository userRepository;
    private final HealthRepository healthRepository;
    private final RecommendationsClient recommendationsClient;
    private final IRecommendationServiceClient recommendationServiceClient;
    private final IProgressServiceClient progressServiceClient;
    private final IMentalHealthServiceClient mentalHealthServiceClient;
    private final INutritionServiceClient nutritionServiceClient;

    public UserCommandService(UserRepository userRepository, HealthRepository healthRepository, RecommendationsClient recommendationsClient, IRecommendationServiceClient recommendationServiceClient, IProgressServiceClient progressServiceClient, IMentalHealthServiceClient mentalHealthServiceClient, INutritionServiceClient nutritionServiceClient) {
        this.userRepository = userRepository;
        this.healthRepository = healthRepository;
        this.recommendationsClient = recommendationsClient;
        this.recommendationServiceClient = recommendationServiceClient;
        this.progressServiceClient = progressServiceClient;
        this.mentalHealthServiceClient = mentalHealthServiceClient;
        this.nutritionServiceClient = nutritionServiceClient;
    }

    // Create User Profile and Health Details
    @Override
    public void createUserDetails(long userId, UserRequestDTO userRequestDTO) {
        // Check if the user already created profile or not
        UserProfile existingUser = userRepository.findByUserId(userId);
        if (existingUser != null) {
            throw new CustomeException(HttpStatus.BAD_REQUEST,
                    "You already create your profile. You can only update your health data. You can't create multiple profiles");
        }

        // Set user Profile & Health Information
        UserProfile userProfile = createAndSaveUserProfile(userId, userRequestDTO);
        HealthDetails healthDetails = createHealthDetails(userId, userRequestDTO);

        // Set HealthDetails fields functionally
        setHealthDetailsFields(healthDetails, userRequestDTO);

        // Set Mental Health Information
        MentalHealth mentalHealth = createMentalHealth(userRequestDTO.getHealthDetails().getMentalHealthDTO());
        mentalHealth.setHealthDetails(healthDetails);

        // Set Physical Health Information
        PhysicalHealth physicalHealth = createPhysicalHealth(userRequestDTO.getHealthDetails().getPhysicalHealthDTO());
        physicalHealth.setHealthDetails(healthDetails);

        // Set Daily Schedule Information
        DailySchedule dailySchedule = createDailySchedule(userRequestDTO.getHealthDetails().getDailyScheduleDTO());
        dailySchedule.setHealthDetails(healthDetails);

        // Set mentalHealth, physicalHealth, and dailySchedule in healthDetails
        healthDetails.setDailySchedule(dailySchedule);
        healthDetails.setMentalHealth(mentalHealth);
        healthDetails.setPhysicalHealth(physicalHealth);

        // Save Database
        userRepository.save(userProfile);
        healthRepository.save(healthDetails);
        // Send data to microservices
        sendToRecommendationMicroservice(healthDetails);
        sendToProgressMicroservice(healthDetails);
        sendToMentalHealthMicroservice(healthDetails);
        sendToNutritionMicroservice(healthDetails);
    }

    // Update Health Information
    @Override
    public void updateHealthInformation(long userId, HealthDetailsDTO healthDetailsDTO) {
        // Check if the user already created profile or not
        HealthDetails healthDetails = healthRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomeException(HttpStatus.BAD_REQUEST,
                        "You can't update without create User profile and health information"));

        // Set value
        healthDetails.setAge(healthDetailsDTO.getAge());
        healthDetails.setWeight(healthDetailsDTO.getWeight());
        healthDetails.setHeight(healthDetailsDTO.getHeight());
        setHealthDetailsFieldsForUpdate(healthDetailsDTO, healthDetails);

        // Convert map to model
        mapDataToPhysicalHealth(healthDetails.getPhysicalHealth(), healthDetailsDTO.getPhysicalHealthDTO());
        mapDataToMentalHealth(healthDetails.getMentalHealth(), healthDetailsDTO.getMentalHealthDTO());
        mapDataToDailySchedule(healthDetails.getDailySchedule(), healthDetailsDTO.getDailyScheduleDTO());

        // Save in Database
        healthRepository.save(healthDetails);
        // Send data to Other Microservices
        sendToRecommendationMicroservice(healthDetails);
        sendToProgressMicroservice(healthDetails);
        sendToMentalHealthMicroservice(healthDetails);
        sendToNutritionMicroservice(healthDetails);
    }

    // Send data to Recommendation Microservices
    private void sendToRecommendationMicroservice(HealthDetails healthDetails) {
        recommendationServiceClient.importUserHealthData(healthDetails)
                .subscribe(
                        response -> log.info("Data received successfully by Recommendation Microservice"),
                        ex -> log.error("Failed to import to Recommendation Microservice: " + ex.getMessage())
                );
    }

    // Send data to Progress Microservices
    private void sendToProgressMicroservice(HealthDetails healthDetails) {
        progressServiceClient.addHealthProgress(healthDetails)
                .subscribe(
                        response -> log.info("Data received successfully by Progress Microservice"),
                        ex -> log.error("Failed to import to Progress Microservice: " + ex.getMessage())
                );
    }

    // Send data to Mental Health Microservices
    private void sendToMentalHealthMicroservice(HealthDetails healthDetails) {
        mentalHealthServiceClient.trackMode(healthDetails)
                .subscribe(
                        response -> log.info("Data received successfully by Mental Health Microservice"),
                        ex -> log.error("Failed to import to Mental Health Microservice: " + ex.getMessage())
                );
    }

    // Send data to Nutrition Microservices
    private void sendToNutritionMicroservice(HealthDetails healthDetails) {
        nutritionServiceClient.addRecommendation(healthDetails)
                .subscribe(
                        response -> log.info("Data received successfully by Nutrition Microservice"),
                        ex -> log.error("Failed to import to Nutrition Microservice: " + ex.getMessage())
                );
    }


    // Map DTO to entity in User Profile
    private UserProfile createAndSaveUserProfile(long userId, UserRequestDTO userRequestDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setAddress(userRequestDTO.getAddress());
        userProfile.setMobile(userRequestDTO.getMobile());
        userProfile.setNationality(userRequestDTO.getNationality());
        userProfile.setHomeDistrict(userRequestDTO.getHomeDistrict());
        return userProfile;
    }

    // Map DTO to entity in Health Details
    private HealthDetails createHealthDetails(long userId, UserRequestDTO userRequestDTO) {
        HealthDetails healthDetails = new HealthDetails();
        healthDetails.setUserId(userId);
        healthDetails.setAge(userRequestDTO.getHealthDetails().getAge());
        healthDetails.setWeight(userRequestDTO.getHealthDetails().getWeight());
        healthDetails.setHeight(userRequestDTO.getHealthDetails().getHeight());
        return healthDetails;
    }

    // Set Health Details from User Details DTO
    private void setHealthDetailsFields(HealthDetails healthDetails, UserRequestDTO userRequestDTO) {
        Map<String, Consumer<HealthDetails>> fieldSetters = Map.of(
                "gender", gender -> healthDetails.setGender(EnumValidation.parseGender(userRequestDTO.getHealthDetails().getGender())),
                "bmi", bmi -> healthDetails.setBmi(calculateBmi(userRequestDTO.getHealthDetails())),
                "bmr", bmr -> healthDetails.setBmr(calculateBmr(userRequestDTO.getHealthDetails())),
                "bloodGroup", bloodGroup -> healthDetails.setBloodGroup(EnumValidation.parseBloodGroup(userRequestDTO.getHealthDetails().getBloodGroup())),
                "goalType", goalType -> healthDetails.setGoalType(EnumValidation.parseGoalType(userRequestDTO.getHealthDetails().getGoalType())),
                "activityLevel", activityLevel -> healthDetails.setActivityLevel(EnumValidation.parseActivityLevel(userRequestDTO.getHealthDetails().getActivityLevel()))
        );

        fieldSetters.forEach((fieldName, setter) -> setter.accept(healthDetails));
    }

    // Calculate BMR
    private Double calculateBmr(HealthDetailsDTO healthDetailsDto) {
        Double weight = healthDetailsDto.getWeight();
        Double height = healthDetailsDto.getHeight();
        String gender = healthDetailsDto.getGender();
        long age = healthDetailsDto.getAge();

        if (gender.equals("Male")) {
            // BMR formula for men (Harris-Benedict Equation):
            // BMR = 88.362 + (13.397 * weight in kg) + (4.799 * height in cm) - (5.677 * age in years)
            return Constants.MALE_BMR_CONSTANT + (Constants.MALE_BMR_WEIGHT_COEFFICIENT * weight)
                    + (Constants.MALE_BMR_HEIGHT_COEFFICIENT * height * 100) - (Constants.MALE_BMR_AGE_COEFFICIENT * age);
        } else {
            // BMR formula for women (Harris-Benedict Equation):
            // BMR = 447.593 + (9.247 * weight in kg) + (3.098 * height in cm) - (4.330 * age in years)
            return Constants.FEMALE_BMR_CONSTANT + (Constants.FEMALE_BMR_WEIGHT_COEFFICIENT * weight)
                    + (Constants.FEMALE_BMR_HEIGHT_COEFFICIENT * height * 100) - (Constants.FEMALE_BMR_AGE_COEFFICIENT * age);
        }
    }

    // Update Health Details and map to Entity
    private void setHealthDetailsFieldsForUpdate(HealthDetailsDTO healthDetailsDTO, HealthDetails healthDetails) {
        Map<String, Consumer<HealthDetails>> fieldSetters = Map.of(
                "gender", gender -> healthDetails.setGender(EnumValidation.parseGender(healthDetailsDTO.getGender())),
                "bmi", bmi -> healthDetails.setBmi(calculateBmi(healthDetailsDTO)),
                "bmr", bmr -> healthDetails.setBmr(calculateBmr(healthDetailsDTO)),
                "bloodGroup", bloodGroup -> healthDetails.setBloodGroup(EnumValidation.parseBloodGroup(healthDetailsDTO.getBloodGroup())),
                "goalType", goalType -> healthDetails.setGoalType(EnumValidation.parseGoalType(healthDetailsDTO.getGoalType())),
                "activityLevel", activityLevel -> healthDetails.setActivityLevel(EnumValidation.parseActivityLevel(healthDetailsDTO.getActivityLevel()))
        );

        fieldSetters.forEach((fieldName, setter) -> setter.accept(healthDetails));
    }

    // Map DTO to Entity Daily Schedule
    private DailySchedule createDailySchedule(DailyScheduleDTO dailyScheduleDTO) {
        DailySchedule dailySchedule = new DailySchedule();
        return mapDataToDailySchedule(dailySchedule, dailyScheduleDTO);
    }

    private DailySchedule mapDataToDailySchedule(DailySchedule dailySchedule, DailyScheduleDTO dailyScheduleDTO) {
        dailySchedule.setWakeTime(dailyScheduleDTO.getWakeTime());
        dailySchedule.setBedTime(dailyScheduleDTO.getBedTime());
        return dailySchedule;
    }

    // Map Mental Health DTO to Mental Health Entity
    private MentalHealth createMentalHealth(MentalHealthDTO mentalHealthDTO) {
        MentalHealth mentalHealth = new MentalHealth();
        return mapDataToMentalHealth(mentalHealth, mentalHealthDTO);
    }

    private MentalHealth mapDataToMentalHealth(MentalHealth mentalHealth, MentalHealthDTO mentalHealthDTO) {
        mentalHealth.setDepression(mentalHealthDTO.isDepression());
        mentalHealth.setAnxiety(mentalHealthDTO.isAnxiety());
        mentalHealth.setPanicDisorder(mentalHealthDTO.isPanicDisorder());
        mentalHealth.setBipolarDisorder(mentalHealthDTO.isBipolarDisorder());
        mentalHealth.setSchizophrenia(mentalHealthDTO.isSchizophrenia());

        mentalHealth.setMode(EnumValidation.parseMode(mentalHealthDTO.getMode()));
        mentalHealth.setStressLevel(EnumValidation.parseStressLevel(mentalHealthDTO.getStressLevel()));
        mentalHealth.setLifeSatisfaction(EnumValidation.parseLifeSatisfaction(mentalHealthDTO.getLifeSatisfaction()));

        return mentalHealth;
    }

    // Map Physical Health DTO to Physical Health Entity
    private PhysicalHealth createPhysicalHealth(PhysicalHealthDTO physicalHealthDTO) {
        PhysicalHealth physicalHealth = new PhysicalHealth();
        return mapDataToPhysicalHealth(physicalHealth, physicalHealthDTO);
    }

    private PhysicalHealth mapDataToPhysicalHealth(PhysicalHealth physicalHealth, PhysicalHealthDTO physicalHealthDTO) {
        physicalHealth.setSmoke(physicalHealthDTO.isSmoke());
        physicalHealth.setDiabetesLevel(EnumValidation.parseDiabetesLevel(physicalHealthDTO.getDiabetesLevel()));
        physicalHealth.setBloodPressure(EnumValidation.parseBloodPressure(physicalHealthDTO.getBloodPressure()));
        physicalHealth.setMotivationLevel(EnumValidation.parseMotivationLevel(physicalHealthDTO.getMotivationLevel()));
        physicalHealth.setAlcoholConsumption(EnumValidation.parseAlcoholConsumption(physicalHealthDTO.getAlcoholConsumption()));
        physicalHealth.setCaffeineConsumption(EnumValidation.parseCaffeineConsumption(physicalHealthDTO.getCaffeineConsumption()));
        physicalHealth.setSleepIssue(EnumValidation.parseSleepIssue(physicalHealthDTO.getSleepIssue()));
        return physicalHealth;
    }

    // Calculate BMI
    private Double calculateBmi(HealthDetailsDTO healthDetailsDto) {
        Double weight = healthDetailsDto.getWeight();
        Double height = healthDetailsDto.getHeight();
        // BMI formula: weight (kg) / (height (m) * height (m))
        return weight / (height * height);
    }
}
