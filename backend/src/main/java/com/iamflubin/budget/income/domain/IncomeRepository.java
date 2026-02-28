package com.iamflubin.budget.income.domain;

import java.util.Optional;
import java.util.UUID;

public interface IncomeRepository {
    void save(Income income);

    Optional<Income> findById(UUID id);

    void delete(Income income);
}
