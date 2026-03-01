package com.iamflubin.budget.analytics.domain;

import java.math.BigDecimal;

public record UserGoals(
        BigDecimal needsRate,
        BigDecimal wantsRate,
        BigDecimal savingsRate
) {
}
