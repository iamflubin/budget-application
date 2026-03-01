package com.iamflubin.budget.analytics.application.command;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

class RatesValidator implements ConstraintValidator<ValidRates, CalculateBudgetCommand> {
    @Override
    public boolean isValid(CalculateBudgetCommand cmd,
                           ConstraintValidatorContext ctx) {
        if (cmd == null) return true;

        BigDecimal needs = cmd.needsRate();
        BigDecimal wants = cmd.wantsRate();
        BigDecimal savings = cmd.savingsRate();

        if (needs == null || wants == null || savings == null) {
            return true;
        }

        if (invalidRate(needs) || invalidRate(wants) || invalidRate(savings)) {
            addViolation(ctx, "Rates must be between 0 and 1");
            return false;
        }

        final BigDecimal sum = needs.add(wants).add(savings);

        if (sum.compareTo(BigDecimal.ONE) > 0) {
            addViolation(ctx, "Total rates cannot exceed 100%");
            return false;
        }

        return true;
    }

    private boolean invalidRate(BigDecimal rate) {
        return rate.compareTo(BigDecimal.ZERO) < 0
                || rate.compareTo(BigDecimal.ONE) > 0;
    }

    private void addViolation(ConstraintValidatorContext ctx, String message) {
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
