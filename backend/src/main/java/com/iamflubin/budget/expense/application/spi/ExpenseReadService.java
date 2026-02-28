package com.iamflubin.budget.expense.application.spi;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Page;

import java.time.LocalDate;

public interface ExpenseReadService {
    Page<Expense> getExpenses(int page, int size, String name, ExpenseCategory category, LocalDate from,
                              LocalDate to, String direction, String... sort);
}
