package com.iamflubin.budget.expense.infrastructure;

import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class ExpenseSpringDataRepository implements ExpenseRepository {
    private final ExpenseJpaRepository repository;

    @Override
    public void save(Expense expense) {
        final ExpenseEntity entity = repository.findById(expense.getId().value())
                .map(incomeEntity -> {
                    incomeEntity.update(expense);
                    return incomeEntity;
                })
                .orElseGet(() -> ExpenseEntity.from(expense));
        repository.save(entity);
    }

    @Override
    public Optional<Expense> findByIdAndUserId(UUID id, UUID userId) {
        final Optional<ExpenseEntity> entity = repository.findByIdAndUserId(id, userId);
        return entity.map(ExpenseEntity::toDomain);
    }

    @Override
    public void delete(Expense expense) {
        repository.deleteById(expense.getId().value());
    }
}
