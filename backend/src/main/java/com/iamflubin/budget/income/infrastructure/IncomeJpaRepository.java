package com.iamflubin.budget.income.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

interface IncomeJpaRepository extends JpaRepository<IncomeEntity, UUID>,
        JpaSpecificationExecutor<IncomeEntity> {
}
