package com.example.userservices.services.impl;

import com.example.userservices.DTO.request.*;
import com.example.userservices.exception.CustomeException;
import com.example.userservices.feignclient.RecommendationsClient;
import com.example.userservices.feignclient.handleException.FeignCustomException;
import com.example.userservices.model.*;
import com.example.userservices.model.Enum.*;
import com.example.userservices.repository.HealthRepository;
import com.example.userservices.repository.UserRepository;
import com.example.userservices.services.IUserCommandService;
import com.example.userservices.utils.Constants;
import com.example.userservices.webclient.RecommendationServiceClient;
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
    private final RecommendationServiceClient recommendationServiceClient;

    public UserCommandService(UserRepository userRepository, HealthRepository healthRepository, RecommendationsClient recommendationsClient, RecommendationServiceClient recommendationServiceClient) {
        this.userRepository = userRepository;
        this.healthRepository = healthRepository;
        this.recommendationsClient = recommendationsClient;
        this.recommendationServiceClient = recommendationServiceClient;
    }

    // Create User Details and Health Details
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
    }

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
        // Send data to microservices
        sendToRecommendationMicroservice(healthDetails);
    }

    // Send data to Recommendation Microservices
    private void sendToRecommendationMicroservice(HealthDetails healthDetails) {
        recommendationServiceClient.importUserHealthData(healthDetails)
                .subscribe(
                        response -> log.info("Data received successfully by Recommendation Microservice"),
                        ex -> log.error("Failed to import to Recommendation Microservice: " + ex.getMessage())
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

    private void setHealthDetailsFields(HealthDetails healthDetails, UserRequestDTO userRequestDTO) {
        Map<String, Consumer<HealthDetails>> fieldSetters = Map.of(
                "gender", gender -> healthDetails.setGender(parseGender(userRequestDTO.getHealthDetails().getGender())),
                "bmi", bmi -> healthDetails.setBmi(calculateBmi(userRequestDTO.getHealthDetails())),
                "bmr", bmr -> healthDetails.setBmr(calculateBmr(userRequestDTO.getHealthDetails())),
                "bloodGroup", bloodGroup -> healthDetails.setBloodGroup(parseBloodGroup(userRequestDTO.getHealthDetails().getBloodGroup())),
                "goalType", goalType -> healthDetails.setGoalType(parseGoalType(userRequestDTO.getHealthDetails().getGoalType())),
                "activityLevel", activityLevel -> healthDetails.setActivityLevel(parseActivityLevel(userRequestDTO.getHealthDetails().getActivityLevel()))
        );

        fieldSetters.forEach((fieldName, setter) -> setter.accept(healthDetails));
    }

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
                "gender", gender -> healthDetails.setGender(parseGender(healthDetailsDTO.getGender())),
                "bmi", bmi -> healthDetails.setBmi(calculateBmi(healthDetailsDTO)),
                "bmr", bmr -> healthDetails.setBmr(calculateBmr(healthDetailsDTO)),
                "bloodGroup", bloodGroup -> healthDetails.setBloodGroup(parseBloodGroup(healthDetailsDTO.getBloodGroup())),
                "goalType", goalType -> healthDetails.setGoalType(parseGoalType(healthDetailsDTO.getGoalType())),
                "activityLevel", activityLevel -> healthDetails.setActivityLevel(parseActivityLevel(healthDetailsDTO.getActivityLevel()))
        );

        fieldSetters.forEach((fieldName, setter) -> setter.accept(healthDetails));
    }

    private DailySchedule createDailySchedule(DailyScheduleDTO dailyScheduleDTO) {
        DailySchedule dailySchedule = new DailySchedule();
        return mapDataToDailySchedule(dailySchedule, dailyScheduleDTO);
    }

    private DailySchedule mapDataToDailySchedule(DailySchedule dailySchedule, DailyScheduleDTO dailyScheduleDTO) {
        dailySchedule.setWakeTime(dailyScheduleDTO.getWakeTime());
        dailySchedule.setBedTime(dailyScheduleDTO.getBedTime());
        return dailySchedule;
    }

    private MentalHealth mapDataToMentalHealth(MentalHealth mentalHealth, MentalHealthDTO mentalHealthDTO) {
        mentalHealth.setDepression(mentalHealthDTO.isDepression());
        mentalHealth.setAnxiety(mentalHealthDTO.isAnxiety());
        mentalHealth.setPanicDisorder(mentalHealthDTO.isPanicDisorder());
        mentalHealth.setBipolarDisorder(mentalHealthDTO.isBipolarDisorder());
        mentalHealth.setSchizophrenia(mentalHealthDTO.isSchizophrenia());

        mentalHealth.setMode(parseMode(mentalHealthDTO.getMode()));
        mentalHealth.setStressLevel(parseStressLevel(mentalHealthDTO.getStressLevel()));
        mentalHealth.setLifeSatisfaction(parseLifeSatisfaction(mentalHealthDTO.getLifeSatisfaction()));

        return mentalHealth;
    }

    private PhysicalHealth mapDataToPhysicalHealth(PhysicalHealth physicalHealth, PhysicalHealthDTO physicalHealthDTO) {
        physicalHealth.setSmoke(physicalHealthDTO.isSmoke());
        physicalHealth.setDiabetesLevel(parseDiabetesLevel(physicalHealthDTO.getDiabetesLevel()));
        physicalHealth.setBloodPressure(parseBloodPressure(physicalHealthDTO.getBloodPressure()));
        physicalHealth.setMotivationLevel(parseMotivationLevel(physicalHealthDTO.getMotivationLevel()));
        physicalHealth.setAlcoholConsumption(parseAlcoholConsumption(physicalHealthDTO.getAlcoholConsumption()));
        physicalHealth.setCaffeineConsumption(parseCaffeineConsumption(physicalHealthDTO.getCaffeineConsumption()));
        physicalHealth.setSleepIssue(parseSleepIssue(physicalHealthDTO.getSleepIssue()));
        return physicalHealth;
    }

    private MentalHealth createMentalHealth(MentalHealthDTO mentalHealthDTO) {
        MentalHealth mentalHealth = new MentalHealth();
        return mapDataToMentalHealth(mentalHealth, mentalHealthDTO);
    }

    private Double calculateBmi(HealthDetailsDTO healthDetailsDto) {
        Double weight = healthDetailsDto.getWeight();
        Double height = healthDetailsDto.getHeight();
        // BMI formula: weight (kg) / (height (m) * height (m))
        return weight / (height * height);
    }

    private BloodGroup parseBloodGroup(String userBloodGroup) {
        try {
            return BloodGroup.valueOf(userBloodGroup.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid blood group. Supported blood groups are A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, and O_NEGATIVE.");
        }
    }

    private GoalType parseGoalType(String userGoalType) {
        try {
            return GoalType.valueOf(userGoalType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid goal type. Supported goal types are LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, and IMPROVE_SLEEP.");
        }
    }

    private ActivityLevel parseActivityLevel(String userActivityLevel) {
        try {
            return ActivityLevel.valueOf(userActivityLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid activity level. Supported activity levels are SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, and VERY_ACTIVE.");
        }
    }

    private Mode parseMode(String modeValue) {
        try {
            return Mode.valueOf(modeValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid mode. Supported modes are HAPPY, SAD, CALM, MANIC.");
        }
    }

    private StressLevel parseStressLevel(String stressLevelValue) {
        try {
            return StressLevel.valueOf(stressLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid stress level. Supported stress levels are LOW, MODERATE, HIGH.");
        }
    }

    private LifeSatisfaction parseLifeSatisfaction(String lifeSatisfactionValue) {
        try {
            return LifeSatisfaction.valueOf(lifeSatisfactionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid life satisfaction level. Supported levels are LOW, MODERATE, HIGH.");
        }
    }

    private PhysicalHealth createPhysicalHealth(PhysicalHealthDTO physicalHealthDTO) {
        PhysicalHealth physicalHealth = new PhysicalHealth();
        return mapDataToPhysicalHealth(physicalHealth, physicalHealthDTO);
    }

    private DiabetesLevel parseDiabetesLevel(String diabetesLevelValue) {
        try {
            return DiabetesLevel.valueOf(diabetesLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid diabetes level. Supported levels are  TYPE_1, TYPE_2, NONE.");
        }
    }

    private Gender parseGender(String userGender) {
        try {
            return Gender.valueOf(userGender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid gender. Supported genders are MALE and FEMALE.");
        }
    }

    private BloodPressure parseBloodPressure(String bloodPressureValue) {
        try {
            return BloodPressure.valueOf(bloodPressureValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid blood pressure level. Supported levels are  HIGH, LOW, NORMAL.");
        }
    }

    private MotivationLevel parseMotivationLevel(String motivationLevelValue) {
        try {
            return MotivationLevel.valueOf(motivationLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid motivation level. Supported levels are LOW, MODERATE, HIGH.");
        }
    }

    private AlcoholConsumption parseAlcoholConsumption(String alcoholConsumptionValue) {
        try {
            return AlcoholConsumption.valueOf(alcoholConsumptionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid alcohol consumption level. Supported levels are NONE, OCCASIONAL, MODERATE, HEAVY.");
        }
    }

    private CaffeineConsumption parseCaffeineConsumption(String caffeineConsumptionValue) {
        try {
            return CaffeineConsumption.valueOf(caffeineConsumptionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid caffeine consumption level. Supported levels are NONE, LOW, MODERATE, HIGH.");
        }
    }

    private SleepIssue parseSleepIssue(String sleepIssueValue) {
        try {
            return SleepIssue.valueOf(sleepIssueValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid sleep issue level. Supported levels are NONE, INSOMNIA, SNORING, SLEEP_APNEA.");
        }
    }
}
