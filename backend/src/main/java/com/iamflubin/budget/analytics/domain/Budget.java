package com.iamflubin.budget.analytics.domain;

import com.iamflubin.budget.shared.domain.Money;

public record Budget(
        Money totalIncome,
        Money totalExpenses,
        Money remaining,
        BudgetCategory needs,
        BudgetCategory wants,
        BudgetCategory savings) {
}
