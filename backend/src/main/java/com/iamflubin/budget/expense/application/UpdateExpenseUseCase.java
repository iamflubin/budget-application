package com.iamflubin.budget.expense.application;

import com.iamflubin.budget.expense.application.command.UpdateExpenseCommand;
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

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class UpdateExpenseUseCase {
    private final ExpenseRepository repository;

    public void execute(final @NonNull @Valid UpdateExpenseCommand command) {
        final Expense expense = repository.findById(command.id()).orElseThrow(
                () -> new ExpenseNotFoundException(command.id())
        );
        expense.update(TransactionName.of(command.name()), Money.of(command.amount()),
                command.date(), command.category()
        );
        repository.save(expense);
        log.info("Expense updated. [id={}]", command.id());
    }
}
