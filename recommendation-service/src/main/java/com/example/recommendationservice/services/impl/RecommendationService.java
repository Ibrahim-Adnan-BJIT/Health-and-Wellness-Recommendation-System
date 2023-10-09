package com.example.recommendationservice.services.impl;

import com.example.recommendationservice.DTO.response.DietResponseDTO;
import com.example.recommendationservice.DTO.response.ExerciseRecommendationResponseDTO;
import com.example.recommendationservice.DTO.response.RecommendedExerciseDTO;
import com.example.recommendationservice.DTO.response.SleepRecommendationResponseDTO;
import com.example.recommendationservice.exception.CustomeException;
import com.example.recommendationservice.external.Enum.DifficultyLevel;
import com.example.recommendationservice.external.Enum.Gender;
import com.example.recommendationservice.external.Enum.GoalType;
import com.example.recommendationservice.model.Diet;
import com.example.recommendationservice.model.Exercise;
import com.example.recommendationservice.model.ExerciseRecommendation;
import com.example.recommendationservice.model.SleepRecommendation;
import com.example.recommendationservice.repository.DietRepository;
import com.example.recommendationservice.repository.ExerciseRecommendationRepository;
import com.example.recommendationservice.repository.ExerciseRepository;
import com.example.recommendationservice.repository.SleepRecommendationRepository;
import com.example.recommendationservice.services.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationService implements IRecommendationService {
    private final SleepRecommendationRepository sleepRepository;
    private final ExerciseRecommendationRepository exerciseRecommendationRepository;
    private final ExerciseRepository exerciseRepository;
    private final DietRepository dietRepository;

    public RecommendationService(SleepRecommendationRepository sleepRepository,
                                 ExerciseRecommendationRepository exerciseRecommendationRepository,
                                 ExerciseRepository exerciseRepository,
                                 DietRepository dietRepository) {
        this.sleepRepository = sleepRepository;
        this.exerciseRecommendationRepository = exerciseRecommendationRepository;
        this.exerciseRepository = exerciseRepository;
        this.dietRepository = dietRepository;
    }



     //  Fetch Sleep Recommendation From DB
    public SleepRecommendationResponseDTO getSleepRecommendationByUserId(long userId) {
        SleepRecommendation sleepRecommendation = sleepRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create you health profile. Please create profile"));

        // Map Entity to DTO and return
        return SleepRecommendationResponseDTO.builder()
                .userId(sleepRecommendation.getUserId())
                .recommendedSleepHours(sleepRecommendation.getRecommendedSleepHours())
                .sleepDuration(sleepRecommendation.getSleepDuration())
                .bedtime(sleepRecommendation.getBedtime())
                .wakeTime(sleepRecommendation.getWakeTime())
                .qualityOfSleep(sleepRecommendation.getQualityOfSleep())
                .recommendedSleepPosition(sleepRecommendation.getRecommendedSleepPosition())
                .nappingRecommendation(sleepRecommendation.getNappingRecommendation())
                .needToTakeMedicine(sleepRecommendation.isNeedToTakeMedicine())
                .needToConsultDoctor(sleepRecommendation.isNeedToConsultDoctor())
                .additionalRecommendations(sleepRecommendation.getAdditionalRecommendations())
                .build();
    }

    // Fetch Diet Recommendations from DB
    public DietResponseDTO getDietRecommendation(long userId) {
        Diet dietRecommendation = dietRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create you health profile. Please create profile"));

        // Map Entity to DTO and return
        return DietResponseDTO
                .builder()
                .userId(dietRecommendation.getUserId())
                .dailyCalorieIntake(dietRecommendation.getDailyCalorieIntake())
                .proteinIntake(dietRecommendation.getProteinIntake())
                .carbohydrateIntake(dietRecommendation.getCarbohydrateIntake())
                .fatIntake(dietRecommendation.getFatIntake())
                .fiberIntake(dietRecommendation.getFiberIntake())
                .vitaminRecommendations(dietRecommendation.getVitaminRecommendations())
                .waterIntake(dietRecommendation.getWaterIntake())
                .recommendedWeight(dietRecommendation.getRecommendedWeight())
                .userBMR(dietRecommendation.getUserBMR())
                .preferredBMR(dietRecommendation.getPreferredBMR())
                .userBMI(dietRecommendation.getUserBMI())
                .preferredBMI(dietRecommendation.getPreferredBMI())
                .moreRecommendations(dietRecommendation.getMoreRecommendations())
                .build();
    }

    /**
     * Fetch Exercise Recommendations. Exercise Recommendations already saved in DB
     * After Fetching Recommendations fetch the necessary Exercise based on
     * Specific User Gender, Age, Height, Weight and  GoalType
     * Then the Exercise duration time will customize based on specific user Gender, Age, Motive, Exercise Difficulty, Exercise Type, GoalType, Equipment
     *
     * @param userId
     * @return ExerciseRecommendationResponseDTO
     */
    public ExerciseRecommendationResponseDTO getExerciseRecommendation(long userId) {
        // Check if User crate his profile of not
        ExerciseRecommendation exerciseRecommendation = exerciseRecommendationRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomeException(HttpStatus.BAD_REQUEST, "You didn't create you health profile. Please create profile"));


        // Based on User Gender, Age, Height, Weight and  GoalType Fetch List of Exercises
        List<Exercise> exercises = exerciseRepository.findByGoalTypeAndMinAgeRequirementLessThanEqualAndMaxAgeRequirementGreaterThanEqual(
                exerciseRecommendation.getGoalType(),
                exerciseRecommendation.getAge(),
                exerciseRecommendation.getAge()
        );

        /*
            After Fetched List of Exercise Customize Exercise Duration Time based on
            Gender, Age, Motive, Exercise Difficulty, Exercise Type, GoalType, Equipment
         */
        List<Exercise> userSpecificUpdatedExercise = customizeExercise(exercises, exerciseRecommendation.getAge(), exerciseRecommendation.getGender());

        // Map Entity to DTO and return
        return ExerciseRecommendationResponseDTO
                .builder()
                .userId(exerciseRecommendation.getUserId())
                .goalType(exerciseRecommendation.getGoalType().toString())
                .gender(exerciseRecommendation.getGender().toString())
                .additionalRecommendations(exerciseRecommendation.getAdditionalRecommendations())
                .recommendedExercise(getRecommendedExerciseDTO(userSpecificUpdatedExercise))
                .build();
    }

    private List<Exercise> customizeExercise(List<Exercise> exercises, long age, Gender gender) {
        return exercises.stream()
                .map(exercise -> {
                    long duration = exercise.getDuration();

                    // Apply gender-specific customization
                    if (gender == Gender.FEMALE) {
                        duration -= 3;
                    } else if (gender == Gender.MALE) {
                        duration += 2;
                    }

                    // Apply age-specific customizations
                    if (age < 10 && duration >= 20) {
                        duration -= 5;
                    } else if (age >= 10 && age <= 15 && duration >= 20) {
                        duration -= 2;
                    } else if (age > 50 && duration >= 20) {
                        duration -= 7;
                    }

                    // Customizations based on Exercise DifficultyLevel and Age Combination
                    DifficultyLevel difficultyLevel = exercise.getDifficultyLevel();
                    if (difficultyLevel == DifficultyLevel.HARD && (age <20 || age >40)) {
                        duration -= 3;
                    } else if (difficultyLevel == DifficultyLevel.HARD && age < 40 && age > 20) {
                        duration += 7;
                    } else if (difficultyLevel == DifficultyLevel.MEDIUM && (age <20 || age >40)) {
                        duration -= 2;
                    } else if (difficultyLevel == DifficultyLevel.MEDIUM && age < 40 && age > 20) {
                        duration += 7;
                    } else if (difficultyLevel == DifficultyLevel.EASY && (age <20 || age >40)) {
                        duration -= 1;
                    } else if (difficultyLevel == DifficultyLevel.EASY && age < 40 && age > 20) {
                        duration += 10;
                    }

                    // Customizations based on GoalType
                    GoalType goalType = exercise.getGoalType();
                    if (goalType == GoalType.LOSE_WEIGHT) {
                        duration += 5;
                    } else if (goalType == GoalType.BUILD_MUSCLE) {
                        duration += 3;
                    } else if (goalType == GoalType.IMPROVE_FITNESS) {
                        duration += 2;
                    } else if (goalType == GoalType.REDUCE_STRESS) {
                        duration += 1;
                    } else if (goalType == GoalType.IMPROVE_SLEEP) {
                        duration += 1;
                    }

                    // Customizations based on equipmentRequired
                    String equipmentRequired = exercise.getEquipmentRequired();
                    if (equipmentRequired != null && !equipmentRequired.isEmpty()) {
                        duration += 5;
                    }

                    // Ensure that duration doesn't go below 0
                    if (duration < 0) {
                        duration = 0;
                    }

                    // Update the exercise's duration
                    exercise.setDuration(duration);
                    return exercise;
                })
                .collect(Collectors.toList());
    }

    // Map list of Exercise to Recommended Exercise DTO
    private List<RecommendedExerciseDTO> getRecommendedExerciseDTO(List<Exercise> exercises) {
        return exercises
                .stream()
                .map(this::mapToRecommendedExerciseDTO).toList();
    }

    // Map Entity to DTO
    private RecommendedExerciseDTO mapToRecommendedExerciseDTO(Exercise exercise) {
        return RecommendedExerciseDTO
                .builder()
                .name(exercise.getName())
                .description(exercise.getDescription())
                .goalType(exercise.getGoalType())
                .minAgeRequirement(exercise.getMinAgeRequirement())
                .maxAgeRequirement(exercise.getMaxAgeRequirement())
                .difficultyLevel(exercise.getDifficultyLevel())
                .equipmentRequired(exercise.getEquipmentRequired())
                .duration(exercise.getDuration())
                .videoTutorialLink(exercise.getVideoTutorialLink())
                .safetyPrecautions(exercise.getSafetyPrecautions())
                .build();
    }
}
