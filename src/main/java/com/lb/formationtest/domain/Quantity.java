package com.lb.formationtest.domain;

import DDD.ValueObject;
import DDD.framework.BigDecimals;
import DDD.framework.SimpleValueObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@ValueObject
public class Quantity extends SimpleValueObject<BigDecimal> {

    static final DecimalFormat df = new DecimalFormat("#,###.000");

    public Quantity(BigDecimal value) {
        super(value);
        BigDecimals.requirePositiveOrZero(value);
    }

    public static Quantity of(String valueStr) {
        return new Quantity(new BigDecimal(valueStr));
    }

    public String asStringWith3digit() {
        return df.format(value);
    }

    @Override
    public String toString() {
        return asStringWith3digit();
    }
}
