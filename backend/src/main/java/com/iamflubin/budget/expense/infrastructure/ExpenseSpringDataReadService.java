package com.iamflubin.budget.expense.infrastructure;

import com.iamflubin.budget.expense.application.spi.ExpenseReadService;
import com.iamflubin.budget.expense.domain.Expense;
import com.iamflubin.budget.expense.domain.ExpenseCategory;
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
class ExpenseSpringDataReadService implements ExpenseReadService {
    private final ExpenseJpaRepository repository;

    @Override
    public Page<Expense> getExpenses(int page, int size, String name, ExpenseCategory category, LocalDate from,
                                     LocalDate to, String direction, String... sort) {
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

        final Specification<ExpenseEntity> spec = ExpenseSpecifications.containsNameIgnoreCase(name)
                .and(ExpenseSpecifications.betweenDates(from, to))
                .and(ExpenseSpecifications.byCategory(category));

        final org.springframework.data.domain.Page<Expense> pageResult = repository
                .findAll(spec, pageable)
                .map(ExpenseEntity::toDomain);

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
