package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.expense.application.spi.ExpenseReadService;
import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Page;
import com.iamflubin.budget.shared.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Validated
@Slf4j
public class GetExpensesUseCase {
    private final ExpenseReadService readService;

    public Page<Expense> execute(final int page, final int size,
                                 final String name, final ExpenseCategory category,
                                 final LocalDate from,
                                 final LocalDate to, final String direction, final String... sort) {
        PaginationUtils.validate(page, size);
        return readService.getExpenses(page, size, name, category, from, to, direction, sort);
    }
}
