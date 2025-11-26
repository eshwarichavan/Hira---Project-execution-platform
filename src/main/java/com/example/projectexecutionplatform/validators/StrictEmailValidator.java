package com.example.projectexecutionplatform.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class StrictEmailValidator implements ConstraintValidator<StrictEmailAddress, String> {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9](?!.*\\.\\.)([A-Za-z0-9._-]{0,62}[A-Za-z0-9])?" +   //for username
                    "@[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?" +         //for domain name
                    "(\\.[A-Za-z0-9]([A-Za-z0-9-]{0,61}[A-Za-z0-9])?)*" +    //for subdomains
                    "\\.[A-Za-z]{2,10}$";                                    //for alphanumeric characters only

    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Regex checkis done here :
        if (!pattern.matcher(email).matches()) {
            return false;
        }

        // No leading dot
        if (email.startsWith(".") || email.endsWith(".")) return false;

        // No double dots anywhere
        if (email.contains("..")) return false;

        String[] parts = email.split("@");
        if (parts.length != 2) return false;

        String username = parts[0];
        String domain = parts[1];

        // Username must not end with dot
        if (username.endsWith(".")) return false;

        // Domain must not start with dot
        if (domain.startsWith(".")) return false;

        // Domain must not contain underscore
        if (domain.contains("_")) return false;

        // No domain segment should start/end with "-"
        for (String seg : domain.split("\\.")) {
            if (seg.startsWith("-") || seg.endsWith("-")) return false;
        }

        return true;
    }
}