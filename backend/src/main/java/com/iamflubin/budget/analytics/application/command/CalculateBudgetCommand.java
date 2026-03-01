package com.iamflubin.budget.analytics.application.command;

import com.iamflubin.budget.shared.validation.ValidDateRange;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@ValidDateRange
@ValidRates
public record CalculateBudgetCommand(
        @NotNull
        LocalDate from,
        @NotNull
        LocalDate to,
        @NotNull
        BigDecimal needsRate,
        @NotNull
        BigDecimal wantsRate,
        @NotNull
        BigDecimal savingsRate
) {
}
