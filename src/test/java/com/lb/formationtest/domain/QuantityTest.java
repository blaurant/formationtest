package com.lb.formationtest.domain;

import DDD.framework.Objects;
import org.junit.Test;

import java.math.BigDecimal;

import static DDD.framework.BigDecimals.CAN_NOT_BE_NEGATIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class QuantityTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }
    @Test
    public void integrityConstraints_neg_value() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Quantity.of("-12.3"))
                .withMessage(CAN_NOT_BE_NEGATIVE);
    }

    @Test
    public void nominal_construction() {
        BigDecimal bigDecimal = new BigDecimal("23.2");
        assertThat(Quantity.of(bigDecimal.toString()).value).isEqualTo(bigDecimal);
    }
    //endregion
}