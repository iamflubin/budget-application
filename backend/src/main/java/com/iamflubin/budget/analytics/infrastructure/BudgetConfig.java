package com.iamflubin.budget.analytics.infrastructure;

import com.iamflubin.budget.analytics.domain.BudgetCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BudgetConfig {
    @Bean
    BudgetCalculator budgetCalculator() {
        return new BudgetCalculator();
    }
}
