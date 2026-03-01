package com.iamflubin.budget.expense.domain;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository {
    void save(Expense expense);
    
    Optional<Expense> findByIdAndUserId(UUID id, UUID userId);

    void delete(Expense expense);
}
