package com.iamflubin.budget.income.infrastructure;

import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.income.domain.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class IncomeSpringDataRepository implements IncomeRepository {
    private final IncomeJpaRepository repository;

    @Override
    public void save(Income income) {
        final IncomeEntity entity = repository.findById(income.getId().value())
                .map(incomeEntity -> {
                    incomeEntity.update(income);
                    return incomeEntity;
                })
                .orElseGet(() -> IncomeEntity.from(income));
        repository.save(entity);
    }


    @Override
    public Optional<Income> findById(UUID id) {
        final Optional<IncomeEntity> entity = repository.findById(id);
        return entity.map(IncomeEntity::toDomain);
    }

    @Override
    public void delete(Income income) {
        repository.deleteById(income.getId().value());
    }
}
