package com.example.userservices.utils;

import com.example.userservices.exception.CustomeException;
import com.example.userservices.model.Enum.*;
import org.springframework.http.HttpStatus;

public class EnumValidation {
    public static SleepIssue parseSleepIssue(String sleepIssueValue) {
        try {
            return SleepIssue.valueOf(sleepIssueValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid sleep issue level. Supported levels are NONE, INSOMNIA, SNORING, SLEEP_APNEA.");
        }
    }

    public static CaffeineConsumption parseCaffeineConsumption(String caffeineConsumptionValue) {
        try {
            return CaffeineConsumption.valueOf(caffeineConsumptionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid caffeine consumption level. Supported levels are NONE, LOW, MODERATE, HIGH.");
        }
    }

    public static AlcoholConsumption parseAlcoholConsumption(String alcoholConsumptionValue) {
        try {
            return AlcoholConsumption.valueOf(alcoholConsumptionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid alcohol consumption level. Supported levels are NONE, OCCASIONAL, MODERATE, HEAVY.");
        }
    }

    public static MotivationLevel parseMotivationLevel(String motivationLevelValue) {
        try {
            return MotivationLevel.valueOf(motivationLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid motivation level. Supported levels are LOW, MODERATE, HIGH.");
        }
    }

    public static BloodPressure parseBloodPressure(String bloodPressureValue) {
        try {
            return BloodPressure.valueOf(bloodPressureValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid blood pressure level. Supported levels are  HIGH, LOW, NORMAL.");
        }
    }

    public static Gender parseGender(String userGender) {
        try {
            return Gender.valueOf(userGender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid gender. Supported genders are MALE and FEMALE.");
        }
    }

    public static DiabetesLevel parseDiabetesLevel(String diabetesLevelValue) {
        try {
            return DiabetesLevel.valueOf(diabetesLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid diabetes level. Supported levels are  TYPE_1, TYPE_2, NONE.");
        }
    }

    public static LifeSatisfaction parseLifeSatisfaction(String lifeSatisfactionValue) {
        try {
            return LifeSatisfaction.valueOf(lifeSatisfactionValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid life satisfaction level. Supported levels are LOW, MODERATE, HIGH.");
        }
    }

    public static StressLevel parseStressLevel(String stressLevelValue) {
        try {
            return StressLevel.valueOf(stressLevelValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid stress level. Supported stress levels are LOW, MODERATE, HIGH.");
        }
    }

    public static Mode parseMode(String modeValue) {
        try {
            return Mode.valueOf(modeValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid mode. Supported modes are HAPPY, SAD, CALM, MANIC.");
        }
    }

    public static BloodGroup parseBloodGroup(String userBloodGroup) {
        try {
            return BloodGroup.valueOf(userBloodGroup.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid blood group. Supported blood groups are A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, and O_NEGATIVE.");
        }
    }

    public static GoalType parseGoalType(String userGoalType) {
        try {
            return GoalType.valueOf(userGoalType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid goal type. Supported goal types are LOSE_WEIGHT, BUILD_MUSCLE, IMPROVE_FITNESS, REDUCE_STRESS, and IMPROVE_SLEEP.");
        }
    }

    public static ActivityLevel parseActivityLevel(String userActivityLevel) {
        try {
            return ActivityLevel.valueOf(userActivityLevel.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomeException(HttpStatus.BAD_REQUEST, "Invalid activity level. Supported activity levels are SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, and VERY_ACTIVE.");
        }
    }


}
