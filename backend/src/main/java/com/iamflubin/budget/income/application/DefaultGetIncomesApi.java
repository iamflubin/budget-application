package com.iamflubin.budget.income.application;

import com.iamflubin.budget.auth.CurrentUserProvider;
import com.iamflubin.budget.income.application.api.GetIncomesApi;
import com.iamflubin.budget.income.application.spi.IncomeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DefaultGetIncomesApi implements GetIncomesApi {
    private final IncomeReadService readService;
    private final CurrentUserProvider userProvider;

    @Override
    public List<IncomeResponse> getIncomes(LocalDate from, LocalDate to) {
        return readService.getIncomes(from, to, userProvider.getCurrentUser().id())
                .stream()
                .map(IncomeResponse::from)
                .toList();
    }
}
