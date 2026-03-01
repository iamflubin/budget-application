package com.iamflubin.budget.analytics.api;

import com.iamflubin.budget.analytics.application.BudgetResponse;
import com.iamflubin.budget.analytics.application.CalculateBudgetUseCase;
import com.iamflubin.budget.analytics.application.command.CalculateBudgetCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1/budget/analytics")
@RequiredArgsConstructor
class BudgetController {
    private final CalculateBudgetUseCase calculateBudgetUseCase;

    @GetMapping
    public ResponseEntity<BudgetResponse> calculate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam BigDecimal needs,
            @RequestParam BigDecimal wants,
            @RequestParam BigDecimal savings) {
        final CalculateBudgetCommand command = new CalculateBudgetCommand(from, to, needs, wants, savings);
        return ResponseEntity.ok(calculateBudgetUseCase.execute(command));
    }
}
