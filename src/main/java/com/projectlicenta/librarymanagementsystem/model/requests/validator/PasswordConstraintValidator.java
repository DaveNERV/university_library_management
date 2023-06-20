package com.projectlicenta.librarymanagementsystem.model.requests.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String passwordField,
                           ConstraintValidatorContext cxt) {
        if (passwordField == null || passwordField.isEmpty()) {
            return true;
        } else {
            return passwordField.matches("^(?=.*[a-z])(?=.*[A-Z]).*$") && passwordField.length() >= 5 && passwordField.length() <= 32;
        }
    }
}