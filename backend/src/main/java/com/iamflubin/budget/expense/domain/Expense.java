package com.iamflubin.budget.expense.domain;

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
public class Expense {
    private final TransactionId id;
    private UUID userId;
    private TransactionName name;
    private Money amount;
    private LocalDate date;
    private ExpenseCategory category;

    private Expense(final @NonNull TransactionId id, final @NonNull UUID userId,
                    final @NonNull TransactionName name, final @NonNull Money amount,
                    final @NonNull LocalDate date, final @NonNull ExpenseCategory category) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public static Expense of(final UUID userId, final TransactionName name, final Money amount,
                             final LocalDate date, final ExpenseCategory category) {
        return new Expense(TransactionId.random(), userId, name, amount, date, category);
    }

    public static Expense of(final TransactionId id, final UUID userId, final TransactionName name,
                             final Money amount, final LocalDate date, final ExpenseCategory category) {
        return new Expense(id, userId, name, amount, date, category);
    }

    public void update(final @NonNull TransactionName name, final @NonNull Money amount,
                       final @NonNull LocalDate date, final @NonNull ExpenseCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
