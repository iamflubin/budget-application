package com.iamflubin.budget.income.application;

import java.util.UUID;

public class IncomeNotFoundException extends RuntimeException {
    public IncomeNotFoundException(final UUID id) {
        super("Income with id " + id + " not found.");
    }
}
