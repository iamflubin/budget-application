package com.iamflubin.budget.income.application;

import com.iamflubin.budget.income.application.spi.IncomeReadService;
import com.iamflubin.budget.income.domain.Income;
import com.iamflubin.budget.shared.domain.Page;
import com.iamflubin.budget.shared.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Validated
@Slf4j
public class GetIncomesUseCase {
    private final IncomeReadService readService;

    public Page<Income> execute(final int page, final int size,
                                final String name, final LocalDate from,
                                final LocalDate to, final String direction, final String... sort) {
        PaginationUtils.validate(page, size);
        return readService.getIncomes(page, size, name, from, to, direction, sort);
    }
}
