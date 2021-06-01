package com.lb.formationtest.domain.billing;

import DDD.framework.SimpleValueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static DDD.framework.BigDecimals.requirePositiveOrZero;

@DDD.ValueObject
public class Amount extends SimpleValueObject<BigDecimal> {

    public static final Amount ZERO = Amount.of(BigDecimal.ZERO);

    public Amount(BigDecimal value) {
        super(value);
        requirePositiveOrZero(value);
    }

    public static Amount of(String strValue) {
        return new Amount(new BigDecimal(strValue));
    }

    public static Amount of(BigDecimal bigDecimal) {
        return new Amount(bigDecimal);
    }

    public String asString() {
        return value.toString();
    }

    public Amount add(Amount amount) {
        return new Amount(value.add(amount.value));
    }

    public Amount multiply(BigDecimal multValue) {
        return new Amount(value.multiply(multValue));
    }

    public Amount truncateAtCents() {
        return new Amount(value.setScale(2, RoundingMode.HALF_EVEN));
    }
}
