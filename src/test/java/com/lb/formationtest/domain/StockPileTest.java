package com.lb.formationtest.domain;

import DDD.framework.Objects;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StockPileTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null_quality() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new StockPile(null, null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }

    @Test
    public void integrityConstraints_null_quantity() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new StockPile(Quality.PET_BF_CLAIR, null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }

    @Test
    public void nominal_construction() {
        Quality quality = Quality.PET_BF_CLAIR;
        Quantity quantity = Quantity.of("12.0");
        assertThat(new StockPile(quality, quantity).quality).isEqualTo(quality);
        assertThat(new StockPile(quality, quantity).quantity).isEqualTo(quantity);
    }
    //endregion
}