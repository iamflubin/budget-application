package com.iamflubin.budget.income.infrastructure;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

final class IncomeSpecifications {

    private IncomeSpecifications() {
    }

    public static Specification<IncomeEntity> containsNameIgnoreCase(final String name) {
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

    public static Specification<IncomeEntity> betweenDates(final LocalDate from, final LocalDate to) {
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

    public static Specification<IncomeEntity> withUserId(final UUID userId) {
        return (root, query, cb) ->
                userId == null ? cb.conjunction() : cb.equal(root.get("userId"), userId);
    }

}
