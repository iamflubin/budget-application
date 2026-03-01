package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Money;

import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        String name,
        Money amount,
        LocalDate date,
        ExpenseCategory category
) {

    public static ExpenseResponse from(final Expense expense) {
        return new ExpenseResponse(expense.getId().value(), expense.getName().value(),
                expense.getAmount(), expense.getDate(), expense.getCategory());
    }
}
