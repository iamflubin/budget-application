package com.iamflubin.budget.income.application.spi;

import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.shared.domain.Page;

import java.time.LocalDate;

public interface IncomeReadService {
    Page<Income> getIncomes(int page, int size, String name,
                            LocalDate from, LocalDate to, String direction, String... sort);
}
