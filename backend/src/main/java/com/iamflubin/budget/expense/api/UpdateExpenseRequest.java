package com.iamflubin.budget.expense.api;

import com.iamflubin.budget.expense.application.command.UpdateExpenseCommand;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.validation.Trim;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record UpdateExpenseRequest(
        @Trim
        @NotBlank
        @Size(max = 225)
        String name,
        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount,
        @NotNull
        LocalDate date,
        @NotNull
        ExpenseCategory category
) {

    public UpdateExpenseCommand toCommand(final UUID id) {
        return new UpdateExpenseCommand(id, name, amount, date, category);
    }
}
