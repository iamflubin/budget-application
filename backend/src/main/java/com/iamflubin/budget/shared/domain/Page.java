package com.iamflubin.budget.shared.domain;

import java.util.Collection;
import java.util.function.Function;

public record Page<T>(int page,
                      int size,
                      long totalElements,
                      int totalPages,
                      boolean last,
                      Collection<T> content) {

    public static <T> Page<T> of(final int page, final int size, final long totalElements,
                                 final int totalPages, final boolean last,
                                 final Collection<T> content) {
        return new Page<>(page, size, totalElements, totalPages, last, content);
    }


    public <U> Page<U> map(final Function<T, U> converter) {
        return new Page<>(page, size, totalElements, totalPages, last, content.stream()
                .map(converter).toList());
    }
}
