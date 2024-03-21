package com.example.LoanManagementSystem.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonthValidator implements ConstraintValidator<ValidMonths, Double> {
    @Override
    public boolean isValid(Double s, ConstraintValidatorContext constraintValidatorContext) {
        return s == 3 || s == 6 || s == 9 || s == 12;
    }
}
