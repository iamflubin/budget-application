package com.iamflubin.budget.shared.domain;

import lombok.NonNull;

import java.util.UUID;

public record TransactionId(@NonNull UUID value) {
    public static TransactionId random() {
        return new TransactionId(UUID.randomUUID());
    }

    public static TransactionId of(UUID value) {
        return new TransactionId(value);
    }
}
