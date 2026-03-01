package com.iamflubin.budget.analytics.application.command;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatesValidator.class)
@Documented
@interface ValidRates {

    String message() default "Invalid budget rates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
