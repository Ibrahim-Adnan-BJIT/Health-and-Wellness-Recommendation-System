package com.example.recommendationservice.customValidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.PositiveOrZero;

public class PositiveOrZeroValidator implements ConstraintValidator<PositiveOrZero, Long> {
    @Override
    public void initialize(PositiveOrZero constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value == null || value >= 0;
    }
}