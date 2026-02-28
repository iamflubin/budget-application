package com.iamflubin.budget.expense.infrastructure;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
import com.iamflubin.budget.shared.domain.Money;
import com.iamflubin.budget.shared.domain.TransactionId;
import com.iamflubin.budget.shared.domain.TransactionName;
import com.iamflubin.budget.shared.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
class ExpenseEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    public static ExpenseEntity from(final Expense expense) {
        final ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setId(expense.getId().value());
        expenseEntity.setName(expense.getName().value());
        expenseEntity.setAmount(expense.getAmount().toBigDecimal());
        expenseEntity.setDate(expense.getDate());
        expenseEntity.setCategory(expense.getCategory());
        return expenseEntity;
    }

    public Expense toDomain() {
        return Expense.of(
                TransactionId.of(id),
                TransactionName.of(name),
                Money.of(amount),
                date,
                category
        );
    }

    public void update(final Expense expense) {
        this.name = expense.getName().value();
        this.amount = expense.getAmount().toBigDecimal();
        this.date = expense.getDate();
        this.category = expense.getCategory();
    }
}
