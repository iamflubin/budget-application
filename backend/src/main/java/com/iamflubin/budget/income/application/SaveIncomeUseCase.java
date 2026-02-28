package com.iamflubin.budget.income.application;

import com.iamflubin.budget.income.application.command.SaveIncomeCommand;
import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.income.domain.IncomeRepository;
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
public class SaveIncomeUseCase {
    private final IncomeRepository repository;

    public UUID execute(final @NonNull @Valid SaveIncomeCommand command) {
        final var income = Income.of(
                TransactionName.of(command.name()),
                Money.of(command.amount()),
                command.date()
        );
        repository.save(income);
        log.info("Income saved. [id={}]", income.getId());
        return income.getId().value();
    }
}
