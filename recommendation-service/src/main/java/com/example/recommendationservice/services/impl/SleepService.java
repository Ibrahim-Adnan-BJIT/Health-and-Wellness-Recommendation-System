package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.external.DailySchedule;
import com.example.recommendationservice.external.Enum.*;
import com.example.recommendationservice.external.HealthDetails;
import com.example.recommendationservice.model.SleepRecommendation;
import com.example.recommendationservice.repository.SleepRecommendationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SleepService {
    private final SleepRecommendationRepository sleepRepository;

    public SleepService(SleepRecommendationRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    // Check if previous data is available or not
    public Optional<SleepRecommendation> getPreviousRecommendation(HealthDetails healthDetails) {
        return sleepRepository.findByUserId(healthDetails.getUserId());
    }

    // Create or Update Recommendation
    public void sleepRecommendation(HealthDetails healthDetails) {
        Optional<SleepRecommendation> previousRecommendation = getPreviousRecommendation(healthDetails);

        if (previousRecommendation.isPresent()) {
            // Update previous recommendation
            buildSleepRecommendation(healthDetails, previousRecommendation.get());
        } else {
            // Create new recommendation
            SleepRecommendation sleepRecommendation = new SleepRecommendation();
            buildSleepRecommendation(healthDetails, sleepRecommendation);
        }
    }

    private void buildSleepRecommendation(HealthDetails healthDetails, SleepRecommendation recommendation) {
        int recommendedSleepHours = calculateRecommendedSleepHours(healthDetails.getAge());
        int sleepDuration = calculateSleepDuration(
                recommendedSleepHours,
                healthDetails.getDailySchedule().getBedTime(),
                healthDetails.getDailySchedule().getWakeTime()
        );

        recommendation.setUserId(healthDetails.getUserId());
        recommendation.setRecommendedSleepHours(recommendedSleepHours);
        recommendation.setBedtime(calculateBedtime(healthDetails.getDailySchedule().getWakeTime(), sleepDuration));
        recommendation.setWakeTime(calculateWakeTime(healthDetails.getDailySchedule().getBedTime(), sleepDuration));
        recommendation.setQualityOfSleep(calculateQualityOfSleep(healthDetails));
        recommendation.setRecommendedSleepPosition(calculateRecommendedSleepPosition(healthDetails));
        recommendation.setSleepDuration(sleepDuration);
        recommendation.setNappingRecommendation(generateNappingRecommendation((healthDetails)));
        recommendation.setNeedToTakeMedicine(needsMedication(healthDetails));
        recommendation.setNeedToConsultDoctor(shouldConsultDoctor(healthDetails));
        recommendation.setAdditionalRecommendations(generateAdditionalRecommendations(healthDetails));

        sleepRepository.save(recommendation);
    }

    // Recommendation for Medication
    private boolean needsMedication(HealthDetails healthDetails) {
        // Calculate recommended sleep hours based on age
        int recommendedSleepHours = calculateRecommendedSleepHours(healthDetails.getAge());

        // Calculate actual sleep duration in minutes
        int actualSleepDuration = calculateSleepDuration(
                recommendedSleepHours,
                healthDetails.getDailySchedule().getBedTime(),
                healthDetails.getDailySchedule().getWakeTime()
        );

        // Retrieve sleep-related health factors
        SleepIssue sleepIssue = healthDetails.getPhysicalHealth().getSleepIssue();
        StressLevel stressLevel = healthDetails.getMentalHealth().getStressLevel();
        BloodPressure bloodPressure = healthDetails.getPhysicalHealth().getBloodPressure();
        AlcoholConsumption alcoholConsumption = healthDetails.getPhysicalHealth().getAlcoholConsumption();
        CaffeineConsumption caffeineConsumption = healthDetails.getPhysicalHealth().getCaffeineConsumption();

        // Determine if medication is needed based on conditions
        return actualSleepDuration < recommendedSleepHours * 60 ||
                sleepIssue == SleepIssue.SLEEP_APNEA ||
                (stressLevel == StressLevel.HIGH && bloodPressure == BloodPressure.HIGH) ||
                alcoholConsumption == AlcoholConsumption.HEAVY ||
                caffeineConsumption == CaffeineConsumption.HIGH;
    }

    // Calculate Recommended Sleep Position
    private String calculateRecommendedSleepPosition(HealthDetails healthDetails) {
        String recommendedSleepPosition = "Back"; // Default recommendation

        // Adjust recommendedSleepPosition based on sleep issues or physical health factors
        if (healthDetails.getPhysicalHealth().getSleepIssue() != null) {
            SleepIssue sleepIssue = healthDetails.getPhysicalHealth().getSleepIssue();

            recommendedSleepPosition = switch (sleepIssue) {
                case SNORING -> "Side";
                case SLEEP_APNEA -> "Elevated";
                case INSOMNIA -> "Back";
                case NONE -> "Varies";
            };
        }

        return recommendedSleepPosition;
    }

    // Recommendation for doctors
    private boolean shouldConsultDoctor(HealthDetails healthDetails) {
        // Retrieve sleep-related health factors
        SleepIssue sleepIssue = healthDetails.getPhysicalHealth().getSleepIssue();
        BloodPressure bloodPressure = healthDetails.getPhysicalHealth().getBloodPressure();
        DiabetesLevel diabetesLevel = healthDetails.getPhysicalHealth().getDiabetesLevel();
        AlcoholConsumption alcoholConsumption = healthDetails.getPhysicalHealth().getAlcoholConsumption();
        CaffeineConsumption caffeineConsumption = healthDetails.getPhysicalHealth().getCaffeineConsumption();
        StressLevel stressLevel = healthDetails.getMentalHealth().getStressLevel();
        Mode mood = healthDetails.getMentalHealth().getMode();

        // Check conditions for doctor's consultation with explanatory comments
        return (sleepIssue == SleepIssue.SLEEP_APNEA || sleepIssue == SleepIssue.SNORING) ||
                (bloodPressure == BloodPressure.HIGH && diabetesLevel == DiabetesLevel.TYPE_2) ||
                (alcoholConsumption == AlcoholConsumption.HEAVY && caffeineConsumption == CaffeineConsumption.HIGH) ||
                (stressLevel == StressLevel.HIGH && mood == Mode.SAD);

        // If none of the conditions match, no doctor's consultation is recommended
    }

    // Calculate Quality of Sleep
    private int calculateQualityOfSleep(HealthDetails healthDetails) {
        int qualityOfSleep = 5; // Default value

        if (healthDetails.getPhysicalHealth().getSleepIssue() != null) {
            SleepIssue sleepIssue = healthDetails.getPhysicalHealth().getSleepIssue();

            switch (sleepIssue) {
                case INSOMNIA -> qualityOfSleep -= 2;
                case SNORING -> qualityOfSleep -= 1;
                case SLEEP_APNEA -> qualityOfSleep -= 3;
                case NONE -> {
                }
            }
        }

        Mode mood = healthDetails.getMentalHealth().getMode();
        StressLevel stressLevel = healthDetails.getMentalHealth().getStressLevel();

        if (mood == Mode.HAPPY && stressLevel == StressLevel.LOW) {
            qualityOfSleep += 1;
        }
        // Ensure the qualityOfSleep value is within the valid range (0 to 10)
        qualityOfSleep = Math.max(0, Math.min(10, qualityOfSleep));

        return qualityOfSleep;
    }

    // Recommendation for Napping
    private String generateNappingRecommendation(HealthDetails healthDetails) {
        StressLevel stressLevel = healthDetails.getMentalHealth().getStressLevel();
        DailySchedule dailySchedule = healthDetails.getDailySchedule();

        if (stressLevel == StressLevel.LOW && dailySchedule != null) {
            return "Consider short, 20-minute naps during the day to reduce stress.";
        }

        if (healthDetails.getAge() >= 65) {
            return "Seniors may benefit from a short nap in the afternoon.";
        }

        // If none of the conditions match, provide a default recommendation
        return "Napping is generally not recommended.";
    }

    // Calculate Recommended Bed Time
    private String calculateBedtime(LocalTime wakeTime, int sleepDuration) {
        LocalTime bedtime = wakeTime.minusMinutes(sleepDuration);
        return bedtime.toString();
    }

    // Calculate Recommended Wake Time
    private String calculateWakeTime(LocalTime bedtime, int sleepDuration) {
        LocalTime wakeTime = bedtime.plusMinutes(sleepDuration);
        return wakeTime.toString();
    }

    // Calculate Recommended Sleep Duration
    public int calculateSleepDuration(int recommendedSleepHours, LocalTime bedTime, LocalTime wakeTime) {
        // Calculate the sleep duration in minutes
        long durationMinutes = bedTime.until(wakeTime, ChronoUnit.MINUTES);

        // If sleep duration is negative or zero, add 24 hours (assuming the wake time is next day)
        if (durationMinutes <= 0) {
            durationMinutes += 24 * 60;
        }

        // Calculate the sleep duration without modifying durationMinutes
        return Math.max((int) durationMinutes, recommendedSleepHours * 60);
    }

    // Calculate recommended sleep duration based on age.
    private int calculateRecommendedSleepHours(long age) {
        if (age >= 18 && age <= 64) {
            return 8;
        } else if (age >= 65) {
            return 7;
        } else if (age >= 13) {
            return 9;
        } else if (age >= 6) {
            return 10;
        } else if (age >= 3) {
            return 11;
        } else {
            return 12;
        }
    }

    // Generate Recommendation
    private List<String> generateAdditionalRecommendations(HealthDetails healthDetails) {
        List<String> recommendations = new ArrayList<>();

        // Recommendations based on sleep, alcohol, caffeine, stress  issues
        recommendations.addAll(generateSleepIssueRecommendations(healthDetails.getPhysicalHealth().getSleepIssue()));
        recommendations.addAll(generateAlcoholRecommendations(healthDetails.getPhysicalHealth().getAlcoholConsumption()));
        recommendations.addAll(generateCaffeineRecommendations(healthDetails.getPhysicalHealth().getCaffeineConsumption()));
        recommendations.addAll(generateStressRecommendations(healthDetails.getMentalHealth().getStressLevel()));

        return recommendations;
    }

    /**
     * Generates sleep issue recommendations based on the type of sleep issue.
     *
     * @param sleepIssue The type of sleep issue.
     * @return A list of recommendations.
     */
    private List<String> generateSleepIssueRecommendations(SleepIssue sleepIssue) {
        List<String> recommendations = new ArrayList<>();

        if (sleepIssue != null) {
            switch (sleepIssue) {
                case INSOMNIA -> recommendations.add("Consider practicing relaxation techniques to improve sleep.");
                case SNORING -> recommendations.add("Sleep on your side to reduce snoring.");
                case SLEEP_APNEA ->
                        recommendations.add("Consult a healthcare professional for sleep apnea diagnosis and treatment.");
                default -> recommendations.add("Identify and address any underlying sleep issues.");
            }
        }

        return recommendations;
    }

    /**
     * Generates alcohol consumption recommendations based on the level of alcohol consumption.
     *
     * @param alcoholConsumption The level of alcohol consumption.
     * @return A list of recommendations.
     */
    private List<String> generateAlcoholRecommendations(AlcoholConsumption alcoholConsumption) {
        List<String> recommendations = new ArrayList<>();

        if (alcoholConsumption != null) {
            switch (alcoholConsumption) {
                case HEAVY -> {
                    recommendations.add("Limit heavy alcohol consumption, especially close to bedtime.");
                    recommendations.add("Consider reducing alcohol intake to improve sleep quality.");
                }
                case MODERATE ->
                        recommendations.add("Moderate alcohol consumption is generally acceptable, but avoid excessive drinking close to bedtime.");
                case OCCASIONAL -> recommendations.add("Low alcohol consumption is less likely to interfere with sleep.");
                case NONE ->
                        recommendations.add("Avoid alcohol consumption, especially close to bedtime, for the best sleep quality.");
            }
        }

        return recommendations;
    }

    /**
     * Generates caffeine consumption recommendations based on the level of caffeine consumption.
     *
     * @param caffeineConsumption The level of caffeine consumption.
     * @return A list of recommendations.
     */
    private List<String> generateCaffeineRecommendations(CaffeineConsumption caffeineConsumption) {
        List<String> recommendations = new ArrayList<>();

        if (caffeineConsumption != null) {
            switch (caffeineConsumption) {
                case HIGH -> {
                    recommendations.add("Reduce caffeine intake, especially in the afternoon and evening.");
                    recommendations.add("Avoid consuming caffeine close to bedtime to improve sleep quality.");
                }
                case MODERATE ->
                        recommendations.add("Moderate caffeine consumption is generally acceptable, but limit intake in the evening.");
                case LOW -> recommendations.add("Low caffeine consumption is less likely to interfere with sleep.");
                case NONE ->
                        recommendations.add("Avoid caffeine consumption, especially in the afternoon and evening, for better sleep.");
            }
        }

        return recommendations;
    }

    /**
     * Generates stress management recommendations based on the level of stress.
     *
     * @param stressLevel The level of stress.
     * @return A list of recommendations.
     */
    private List<String> generateStressRecommendations(StressLevel stressLevel) {
        List<String> recommendations = new ArrayList<>();

        if (stressLevel != null) {
            switch (stressLevel) {
                case HIGH -> {
                    recommendations.add("Practice stress-reduction techniques such as meditation and deep breathing.");
                    recommendations.add("Consider regular exercise to alleviate stress and improve sleep.");
                    recommendations.add("Limit exposure to stressors, especially close to bedtime.");
                }
                case MODERATE ->
                        recommendations.add("Manage stress through relaxation exercises and a calming bedtime routine.");
                case LOW ->
                        recommendations.add("Maintain a low-stress environment and engage in relaxing activities before sleep.");
            }
        }

        return recommendations;
    }

}
