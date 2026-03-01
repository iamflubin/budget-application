package com.iamflubin.budget.income.application;

import com.iamflubin.budget.auth.CurrentUserProvider;
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
    private final CurrentUserProvider userProvider;
    private final IncomeRepository repository;

    public void execute(final @NonNull UUID id) {
        final Income income = repository.findByIdAndUserId(id, userProvider.getCurrentUser().id()).orElseThrow(
                () -> new IncomeNotFoundException(id));
        repository.delete(income);
        log.info("Income deleted. [id={}] ", id);
    }
}
