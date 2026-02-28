package com.iamflubin.budget.income.application.command;

import com.iamflubin.budget.shared.validation.Trim;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateIncomeCommand(
        @NotNull
        UUID id,
        @Trim
        @NotBlank
        @Size(max = 225)
        String name,
        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,
        @NotNull
        LocalDate date
) {
}
