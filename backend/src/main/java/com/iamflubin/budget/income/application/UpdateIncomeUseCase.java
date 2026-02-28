package com.iamflubin.budget.income.application;

import com.iamflubin.budget.income.application.command.UpdateIncomeCommand;
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

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class UpdateIncomeUseCase {
    private final IncomeRepository repository;

    public void execute(final @NonNull @Valid UpdateIncomeCommand command) {
        final Income income = repository.findById(command.id()).orElseThrow(
                () -> new IncomeNotFoundException(command.id())
        );
        income.update(TransactionName.of(command.name()), Money.of(command.amount()),
                command.date()
        );
        repository.save(income);
        log.info("Income updated. [id={}]", command.id());
    }
}
