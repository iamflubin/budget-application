package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.expense.application.command.SaveExpenseCommand;
import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseRepository;
import com.iamflubin.budget.shared.domain.Money;
import com.iamflubin.budget.shared.domain.TransactionName;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class SaveExpenseUseCase {
    private final ExpenseRepository expenseRepository;

    public UUID execute(final @NonNull @Valid SaveExpenseCommand command) {
        final var expense = Expense.of(
                TransactionName.of(command.name()),
                Money.of(command.amount()),
                command.date(),
                command.category()
        );
        expenseRepository.save(expense);
        log.info("Expense saved. [id={}]", expense.getId());
        return expense.getId().value();
    }
}
