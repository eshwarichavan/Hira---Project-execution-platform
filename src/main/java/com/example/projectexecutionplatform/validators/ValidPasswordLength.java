package com.example.projectexecutionplatform.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordLengthValidator.class)
public @interface ValidPasswordLength {

    String message() default "Password length must be 6 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
