package com.iamflubin.budget.expense.application.api;

import com.iamflubin.budget.expense.application.ExpenseResponse;

import java.time.LocalDate;
import java.util.List;

public interface GetExpensesApi {
    List<ExpenseResponse> getExpenses(LocalDate from, LocalDate to);
}
