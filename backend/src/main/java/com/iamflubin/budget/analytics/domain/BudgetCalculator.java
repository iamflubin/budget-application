package com.iamflubin.budget.analytics.domain;

import com.iamflubin.budget.expense.application.ExpenseResponse;
import com.iamflubin.budget.income.application.IncomeResponse;
import com.iamflubin.budget.shared.domain.Money;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class BudgetCalculator {

    public Budget calculateBudget(final @NonNull List<IncomeResponse> incomes,
                                  final @NonNull List<ExpenseResponse> expenses,
                                  final @NonNull UserGoals userGoals) {
        validateRates(userGoals);

        final Money totalIncome = incomes.stream()
                .map(IncomeResponse::amount)
                .reduce(Money.zero(), Money::add);

        final Money totalExpenses = expenses.stream()
                .map(ExpenseResponse::amount)
                .reduce(Money.zero(), Money::add);

        final Money remaining = totalIncome.subtract(totalExpenses);

        return new Budget(
                totalIncome,
                totalExpenses,
                remaining,
                buildCategory(totalIncome, totalExpenses, userGoals.needsRate()),
                buildCategory(totalIncome, totalExpenses, userGoals.wantsRate()),
                buildCategory(totalIncome, totalExpenses, userGoals.savingsRate())
        );
    }

    private BudgetCategory buildCategory(final Money totalIncome,
                                         final Money totalExpenses,
                                         final BigDecimal rate) {
        final BigDecimal goal = totalIncome
                .multiply(rate)
                .toBigDecimal();

        final BigDecimal total = totalExpenses.toBigDecimal();

        final BigDecimal balance = goal.subtract(total);

        return new BudgetCategory(goal, total, balance);
    }

    private void validateRates(final UserGoals goals) {
        final BigDecimal sum = goals.needsRate()
                .add(goals.wantsRate())
                .add(goals.savingsRate());

        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("Budget rates must equal 100%");
        }
    }
}
