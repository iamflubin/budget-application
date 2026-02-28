package com.iamflubin.budget.income.api;

import com.iamflubin.budget.income.domain.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record IncomeResponse(
        UUID id,
        String name,
        BigDecimal amount,
        LocalDate date
) {

    public static IncomeResponse from(final Income income) {
        return new IncomeResponse(income.getId().value(), income.getName().value(),
                income.getAmount().toBigDecimal(), income.getDate());
    }
}
