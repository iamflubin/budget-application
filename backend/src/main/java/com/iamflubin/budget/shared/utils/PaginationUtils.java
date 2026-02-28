package com.iamflubin.budget.shared.utils;

public final class PaginationUtils {
    public static final int MAX_PAGE_SIZE = 30;

    private PaginationUtils() {
    }

    public static void validate(final int page, final int size) {
        if (page < 0 || size < 1) {
            throw new IllegalArgumentException("Invalid page or size");
        }

        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Invalid page size");
        }
    }
}
