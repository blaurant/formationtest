package com.lb.formationtest.domain.billing;

import DDD.framework.SimpleValueObject;

import java.math.BigDecimal;
import java.math.MathContext;

@DDD.ValueObject
public class Index extends SimpleValueObject<BigDecimal> {
    public static final Index NAI = new Index("-99999999999");
    public static final String NOT_AVAILABLE = "NAI";

    public Index(BigDecimal value) {
        super(value);
    }

    public Index(String valueString) {
        super(new BigDecimal(valueString));
    }

    public static Index of(String valueString) {
        return new Index(valueString);
    }

    public BigDecimal divide(Index index) {
        return value.divide(index.value, MathContext.DECIMAL128);
    }

    public String asString() {
        return NAI.equals(this) ? NOT_AVAILABLE : value.toString();
    }
}
