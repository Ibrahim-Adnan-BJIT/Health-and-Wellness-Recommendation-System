package com.mode.main.service;

import com.mode.main.entity.ExerciseEntity;
import com.mode.main.exception.CustomException;
import com.mode.main.repository.ExerciseRepository;
import com.mode.main.sourcemodel.HealthDetails;
import com.mode.main.sourcemodel.MentalHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    private final ExerciseRepository exerciseRepository;


    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ExerciseEntity exerciseRecommendation(HealthDetails mode) {
        ExerciseEntity exerciseEntity = new ExerciseEntity();
        MentalHealth mentalHealth = new MentalHealth();
        long userId = mode.getUserId();
//

        mentalHealth = mode.getMentalHealth();
        if (mentalHealth.isDepression() && mentalHealth.isAnxiety()
                && !mentalHealth.isPanicDisorder() && !mentalHealth.isSchizophrenia()) {

            Optional<ExerciseEntity> find = exerciseRepository.findByUserId(userId);
            if (find.isPresent()) {
                exerciseEntity = find.get();
                exerciseEntity.setExerciseName("Meditation, Yoga");
                exerciseEntity.setDuration("20, 30 Minutes");
                exerciseEntity.setTotalDay("30 Days");
            } else {
                exerciseEntity.setUserId(userId);
                exerciseEntity.setExerciseName("Meditation, Yoga");
                exerciseEntity.setDuration("20, 30 Minutes");
                exerciseEntity.setTotalDay("30 Days");
            }

        } else if (!mentalHealth.isDepression() && !mentalHealth.isAnxiety()
                && !mentalHealth.isPanicDisorder() && !mentalHealth.isSchizophrenia()) {

            Optional<ExerciseEntity> find = exerciseRepository.findByUserId(userId);
            if (find.isPresent()) {
                exerciseEntity = find.get();
                exerciseEntity.setExerciseName("You are fit. Just do regular exercise");
                exerciseEntity.setDuration("30 Minutes");
                exerciseEntity.setTotalDay("Continue");
            } else {
                exerciseEntity.setUserId(userId);
                exerciseEntity.setExerciseName("Just do regular exercise");
                exerciseEntity.setDuration("30 Minutes");
                exerciseEntity.setTotalDay("Continue");
            }

        } else if (mentalHealth.isDepression() && !mentalHealth.isAnxiety()
                && !mentalHealth.isPanicDisorder() && !mentalHealth.isSchizophrenia()) {

            Optional<ExerciseEntity> find = exerciseRepository.findByUserId(userId);
            if (find.isPresent()) {
                exerciseEntity = find.get();
                exerciseEntity.setExerciseName("Aerobic exercise: running, swimming, biking, walking, dancing, and hiking");
                exerciseEntity.setDuration("20 Minutes");
                exerciseEntity.setTotalDay("Continue");
            } else {
                exerciseEntity.setUserId(userId);
                exerciseEntity.setExerciseName("running, swimming, biking, walking, dancing, and hiking");
                exerciseEntity.setDuration("20 Minutes");
                exerciseEntity.setTotalDay("Continue");
            }

        } else if (mentalHealth.isDepression() && mentalHealth.isAnxiety()
                && !mentalHealth.isPanicDisorder() && mentalHealth.isSchizophrenia()) {

            Optional<ExerciseEntity> find = exerciseRepository.findByUserId(userId);
            if (find.isPresent()) {
                exerciseEntity = find.get();
                exerciseEntity.setExerciseName("Aerobic exercise: running, swimming, biking, walking, dancing, and hiking");
                exerciseEntity.setDuration("20 Minutes");
                exerciseEntity.setTotalDay("Continue");
            } else {
                exerciseEntity.setUserId(userId);
                exerciseEntity.setExerciseName("running, swimming, biking, walking, dancing, and hiking");
                exerciseEntity.setDuration("20 Minutes");
                exerciseEntity.setTotalDay("Continue");
            }

        } else if (mentalHealth.isDepression() && mentalHealth.isAnxiety()
                && mentalHealth.isPanicDisorder() && mentalHealth.isSchizophrenia()) {

            Optional<ExerciseEntity> find = exerciseRepository.findByUserId(userId);
            if (find.isPresent()) {
                exerciseEntity = find.get();
                exerciseEntity.setExerciseName("Please you have to consultant with a doctor. Because you have serious health conditions");
                exerciseEntity.setDuration("Doctors recommendation");
                exerciseEntity.setTotalDay("Doctors recommendation");
            } else {
                exerciseEntity.setUserId(userId);
                exerciseEntity.setExerciseName("Please you have to consultant with a doctor.");
                exerciseEntity.setDuration("Doctors recommendation");
                exerciseEntity.setTotalDay("Doctors recommendation");
            }

        }
        return exerciseRepository.save(exerciseEntity);
    }

    public ExerciseEntity getExercises(Long userId) {
        Optional<ExerciseEntity> exercises = exerciseRepository.findByUserId(userId);
        if (exercises.isEmpty()) {
            throw new CustomException("Exercise not found for user with ID: " + userId);
        }
        return exercises.get();
    }

}
