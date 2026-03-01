package com.iamflubin.budget.income.application;

import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.shared.domain.Money;

import java.time.LocalDate;
import java.util.UUID;

public record IncomeResponse(
        UUID id,
        String name,
        Money amount,
        LocalDate date
) {

    public static IncomeResponse from(final Income income) {
        return new IncomeResponse(income.getId().value(), income.getName().value(),
                income.getAmount(), income.getDate());
    }
}
