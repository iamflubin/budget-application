package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeleteExpenseUseCase {
    private final ExpenseRepository repository;

    public void execute(final @NonNull UUID id) {
        final Expense expense = repository.findById(id).orElseThrow(
                () -> new ExpenseNotFoundException(id));
        repository.delete(expense);
        log.info("Expense deleted. [id={}] ", id);
    }
}
