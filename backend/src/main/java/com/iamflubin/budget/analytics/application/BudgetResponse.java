package com.iamflubin.budget.analytics.application;

import com.iamflubin.budget.analytics.domain.Budget;

import java.math.BigDecimal;

public record BudgetResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal remaining,
        BudgetCategoryResponse needs,
        BudgetCategoryResponse wants,
        BudgetCategoryResponse savings
) {

    public static BudgetResponse from(final Budget budget) {
        return new BudgetResponse(
                budget.totalIncome().toBigDecimal(),
                budget.totalExpenses().toBigDecimal(),
                budget.remaining().toBigDecimal(),
                new BudgetCategoryResponse(budget.needs().goal(), budget.needs().total(), budget.needs().remaining()),
                new BudgetCategoryResponse(budget.wants().goal(), budget.wants().total(), budget.wants().remaining()),
                new BudgetCategoryResponse(budget.savings().goal(), budget.savings().total(), budget.savings().remaining())
        );
    }
}
