package com.iamflubin.budget.income.application.api;

import com.iamflubin.budget.income.application.IncomeResponse;

import java.time.LocalDate;
import java.util.List;

public interface GetIncomesApi {
    List<IncomeResponse> getIncomes(LocalDate from, LocalDate to);
}
