package com.iamflubin.budget.income.domain;

import com.iamflubin.budget.shared.domain.Money;
import com.iamflubin.budget.shared.domain.TransactionId;
import com.iamflubin.budget.shared.domain.TransactionName;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
public class Income {
    private final TransactionId id;
    private final UUID userId;
    private TransactionName name;
    private Money amount;
    private LocalDate date;

    private Income(final @NonNull TransactionId id, final @NonNull UUID userId,
                   final @NonNull TransactionName name, final @NonNull Money amount,
                   final @NonNull LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public static Income of(final UUID userId, final TransactionName name, final Money amount,
                            final LocalDate date) {
        return new Income(TransactionId.random(), userId, name, amount, date);
    }

    public static Income of(final TransactionId id, final UUID userId, final TransactionName name,
                            final Money amount, final LocalDate date) {
        return new Income(id, userId, name, amount, date);
    }

    public void update(final @NonNull TransactionName name, final @NonNull Money amount,
                       final @NonNull LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income income)) return false;
        return Objects.equals(id, income.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
