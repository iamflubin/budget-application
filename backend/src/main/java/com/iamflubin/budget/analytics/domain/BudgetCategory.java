package com.iamflubin.budget.analytics.domain;

import java.math.BigDecimal;

public record BudgetCategory(
        BigDecimal goal,
        BigDecimal total,
        BigDecimal remaining
) {
}
