package com.iamflubin.budget.analytics.application;

import com.iamflubin.budget.analytics.application.command.CalculateBudgetCommand;
import com.iamflubin.budget.analytics.domain.Budget;
import com.iamflubin.budget.analytics.domain.BudgetCalculator;
import com.iamflubin.budget.analytics.domain.UserGoals;
import com.iamflubin.budget.expense.application.ExpenseResponse;
import com.iamflubin.budget.expense.application.api.GetExpensesApi;
import com.iamflubin.budget.income.application.IncomeResponse;
import com.iamflubin.budget.income.application.api.GetIncomesApi;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class CalculateBudgetUseCase {
    GetIncomesApi getIncomesApi;
    GetExpensesApi getExpensesApi;
    BudgetCalculator calculator;

    public BudgetResponse execute(final @NonNull @Valid CalculateBudgetCommand command) {
        final LocalDate from = command.from();
        final LocalDate to = command.to();
        final List<IncomeResponse> incomes = getIncomesApi.getIncomes(from, to);
        final List<ExpenseResponse> expenses = getExpensesApi.getExpenses(from, to);
        final Budget budget = calculator.calculateBudget(incomes, expenses, new UserGoals(
                command.needsRate(),
                command.wantsRate(),
                command.savingsRate()
        ));
        return BudgetResponse.from(budget);
    }
}
