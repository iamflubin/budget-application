package com.iamflubin.budget.income.application;

import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.income.domain.IncomeRepository;
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
public class DeleteIncomeUseCase {
    private final IncomeRepository repository;

    public void execute(final @NonNull UUID id) {
        final Income income = repository.findById(id).orElseThrow(
                () -> new IncomeNotFoundException(id));
        repository.delete(income);
        log.info("Income deleted. [id={}] ", id);
    }
}
