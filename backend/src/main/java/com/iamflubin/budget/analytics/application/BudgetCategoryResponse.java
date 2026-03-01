package com.iamflubin.budget.analytics.application;

import java.math.BigDecimal;

public record BudgetCategoryResponse(
        BigDecimal goal,
        BigDecimal total,
        BigDecimal remaining
) {
}
