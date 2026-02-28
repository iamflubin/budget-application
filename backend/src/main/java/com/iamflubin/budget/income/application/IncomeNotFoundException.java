package com.iamflubin.budget.income.application;

import java.io.Serial;
import java.util.UUID;

public class IncomeNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2291554218570028427L;

    public IncomeNotFoundException(final UUID id) {
        super("Income with id " + id + " not found.");
    }
}
