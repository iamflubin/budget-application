package com.iamflubin.budget.expense.domain;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository {
    void save(Expense expense);

    Optional<Expense> findById(UUID id);

    void delete(Expense expense);
}
