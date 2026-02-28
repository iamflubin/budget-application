package com.iamflubin.budget.expense.api;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        String name,
        BigDecimal amount,
        LocalDate date,
        ExpenseCategory category
) {

    public static ExpenseResponse from(final Expense expense) {
        return new ExpenseResponse(expense.getId().value(), expense.getName().value(),
                expense.getAmount().toBigDecimal(), expense.getDate(), expense.getCategory());
    }
}
