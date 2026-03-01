package com.iamflubin.budget.expense.application.spi;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseReadService {
    Page<Expense> getExpenses(int page, int size, String name, ExpenseCategory category, LocalDate from,
                              LocalDate to, UUID userId, String direction, String... sort);

    List<Expense> getExpenses(LocalDate from, LocalDate to, UUID userId);
}
