package com.iamflubin.budget.income.api;

import com.iamflubin.budget.income.application.command.UpdateIncomeCommand;
import com.iamflubin.budget.shared.validation.Trim;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateIncomeRequest(
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

    public UpdateIncomeCommand toCommand(final UUID id) {
        return new UpdateIncomeCommand(id, name, amount, date);
    }
}
