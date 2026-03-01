package com.iamflubin.budget.expense.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

interface ExpenseJpaRepository extends JpaRepository<ExpenseEntity, UUID>, JpaSpecificationExecutor<ExpenseEntity> {
    Optional<ExpenseEntity> findByIdAndUserId(UUID id, UUID userId);
}
