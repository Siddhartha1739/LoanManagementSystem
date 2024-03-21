package com.example.LoanManagementSystem.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MonthValidator.class)

public @interface ValidMonths {
    public String message() default "Invalid months value. Allowed values are 3, 6, 9, 12";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
