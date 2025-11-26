package com.example.projectexecutionplatform.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordLengthValidator implements ConstraintValidator<ValidPasswordLength, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null) {
            return false;
        }

        return password.length() == 6; // password length 6 characters long
    }

}