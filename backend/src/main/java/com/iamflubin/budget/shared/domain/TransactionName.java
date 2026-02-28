package com.iamflubin.budget.shared.domain;

import lombok.NonNull;

public record TransactionName(@NonNull String value) {
    public TransactionName {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Transaction name cannot be blank");
        }
    }

    public static TransactionName of(String value) {
        return new TransactionName(value);
    }
}
