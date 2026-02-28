package com.iamflubin.budget.income.infrastructure;

import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.shared.infrastructure.BaseEntity;
import com.iamflubin.budget.shared.domain.Money;
import com.iamflubin.budget.shared.domain.TransactionId;
import com.iamflubin.budget.shared.domain.TransactionName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "income")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
class IncomeEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate date;


    public static IncomeEntity from(final Income income) {
        final var entity = new IncomeEntity();
        entity.setId(income.getId().value());
        entity.setName(income.getName().value());
        entity.setAmount(income.getAmount().toBigDecimal());
        entity.setDate(income.getDate());
        return entity;
    }

    public Income toDomain() {
        return Income.of(
                TransactionId.of(getId()),
                TransactionName.of(name),
                Money.of(amount),
                date
        );
    }

    public void update(final Income income) {
        name = income.getName().value();
        amount = income.getAmount().toBigDecimal();
        date = income.getDate();
    }
}
