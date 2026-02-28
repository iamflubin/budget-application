package com.iamflubin.budget.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {

    String message() default "'to' must be after or equal to 'from'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
