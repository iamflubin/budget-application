package com.iamflubin.budget.shared.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = normalize(value);
    }

    public static Money of(BigDecimal value) {
        Objects.requireNonNull(value, "Money cannot be null");
        return new Money(value);
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money add(Money other) {
        require(other);
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        require(other);
        return new Money(this.value.subtract(other.value));
    }

    public Money multiply(BigDecimal factor) {
        Objects.requireNonNull(factor);
        return new Money(this.value.multiply(factor));
    }

    public Money multiply(double factor) {
        return multiply(BigDecimal.valueOf(factor));
    }

    public Money divide(BigDecimal divisor) {
        Objects.requireNonNull(divisor);
        if (divisor.signum() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new Money(this.value.divide(divisor, SCALE, ROUNDING));
    }

    public Money abs() {
        return new Money(value.abs());
    }

    public Money negate() {
        return new Money(value.negate());
    }

    public boolean isZero() {
        return value.signum() == 0;
    }

    public boolean isPositive() {
        return value.signum() > 0;
    }

    public boolean isNegative() {
        return value.signum() < 0;
    }

    public boolean greaterThan(Money other) {
        return compareTo(other) > 0;
    }

    public boolean lessThan(Money other) {
        return compareTo(other) < 0;
    }

    @Override
    public int compareTo(Money other) {
        require(other);
        return this.value.compareTo(other.value);
    }

    public BigDecimal toBigDecimal() {
        return value;
    }

    public long toCents() {
        return value.movePointRight(SCALE).longValueExact();
    }

    private static BigDecimal normalize(BigDecimal v) {
        return v.setScale(SCALE, ROUNDING);
    }

    private static void require(Money other) {
        Objects.requireNonNull(other, "Money cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return value.compareTo(money.value) == 0;
    }

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
