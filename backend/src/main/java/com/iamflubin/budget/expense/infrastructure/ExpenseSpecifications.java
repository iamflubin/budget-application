package com.iamflubin.budget.expense.infrastructure;

import com.iamflubin.budget.expense.domain.ExpenseCategory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

final class ExpenseSpecifications {

    private ExpenseSpecifications() {
    }

    public static Specification<ExpenseEntity> containsNameIgnoreCase(final String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }

            final String pattern = "%" + name.trim().toLowerCase() + "%";

            return cb.like(
                    cb.lower(root.get("name")),
                    pattern
            );
        };
    }

    public static Specification<ExpenseEntity> betweenDates(final LocalDate from, final LocalDate to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return cb.conjunction();

            final var path = root.<LocalDate>get("date");

            if (from != null && to != null) {
                return cb.between(path, from, to);
            }
            if (from != null) {
                return cb.greaterThanOrEqualTo(path, from);
            }
            return cb.lessThanOrEqualTo(path, to);
        };
    }

    public static Specification<ExpenseEntity> byCategory(final ExpenseCategory category) {
        return (root, query, cb) -> {
            if (category == null) return cb.conjunction();

            return cb.equal(root.get("category"), category);
        };
    }

}
