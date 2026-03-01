package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.auth.CurrentUserProvider;
import com.iamflubin.budget.expense.application.api.GetExpensesApi;
import com.iamflubin.budget.expense.application.spi.ExpenseReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DefaultGetExpensesApi implements GetExpensesApi {
    private final ExpenseReadService readService;
    private final CurrentUserProvider userProvider;

    @Override
    public List<ExpenseResponse> getExpenses(LocalDate from, LocalDate to) {
        return readService.getExpenses(from, to, userProvider.getCurrentUser().id())
                .stream()
                .map(ExpenseResponse::from)
                .toList();
    }
}
