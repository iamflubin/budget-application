package com.iamflubin.budget.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            var fromField = value.getClass().getDeclaredField("from");
            var toField = value.getClass().getDeclaredField("to");

            fromField.setAccessible(true);
            toField.setAccessible(true);

            LocalDate from = (LocalDate) fromField.get(value);
            LocalDate to = (LocalDate) toField.get(value);

            if (from == null || to == null) {
                return true;
            }

            if (!to.isBefore(from)) {
                return true;
            }

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "'to' must be after or equal to 'from'"
            ).addPropertyNode("to").addConstraintViolation();

            return false;

        } catch (Exception e) {
            return true;
        }
    }
}