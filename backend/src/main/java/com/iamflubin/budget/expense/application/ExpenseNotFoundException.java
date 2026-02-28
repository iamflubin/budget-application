package com.iamflubin.budget.expense.application;

import java.io.Serial;
import java.util.UUID;

public class ExpenseNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3009948099036367850L;

    public ExpenseNotFoundException(final UUID id) {
        super("Expense with id " + id + " not found.");
    }
}
