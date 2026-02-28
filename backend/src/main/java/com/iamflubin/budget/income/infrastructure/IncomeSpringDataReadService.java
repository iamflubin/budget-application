package com.iamflubin.budget.income.infrastructure;

import com.iamflubin.budget.income.application.spi.IncomeReadService;
import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.shared.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
class IncomeSpringDataReadService implements IncomeReadService {
    private final IncomeJpaRepository repository;

    @Override
    public Page<Income> getIncomes(int page, int size, String name,
                                   LocalDate from, LocalDate to,
                                   String direction, String... sort) {
        Sort sortObj = Sort.unsorted();

        if (sort != null && sort.length > 0) {
            final Sort.Direction dir = Sort.Direction.fromString(direction);
            sortObj = Sort.by(
                    Arrays.stream(sort)
                            .map(field -> new Sort.Order(dir, field))
                            .toList()
            );
        }

        final Pageable pageable = PageRequest.of(page, size, sortObj);

        final Specification<IncomeEntity> spec = IncomeSpecifications.containsNameIgnoreCase(name)
                .and(IncomeSpecifications.betweenDates(from, to));

        final org.springframework.data.domain.Page<Income> pageResult = repository
                .findAll(spec, pageable)
                .map(IncomeEntity::toDomain);

        return Page.of(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast(),
                pageResult.getContent()
        );
    }
}
