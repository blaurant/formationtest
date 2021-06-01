package com.lb.formationtest.domain;

import DDD.framework.EntityId;
import DDD.framework.Objects;
import org.junit.Test;

import static com.lb.formationtest.domain.Stock.EMPTY_STOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class WESCTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null_id() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new WESC(null, null, null))
                .withMessage(EntityId.ID_IS_NULL);
    }

    @Test
    public void integrityConstraints_null_name() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new WESC(WESC.Id.generate(), null, null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }

    @Test
    public void integrityConstraints_null_stock() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new WESC(WESC.Id.generate(), Name.of("name"), null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }

    @Test
    public void nominal_construction() {
        WESC.Id wescId = WESC.Id.generate();
        Name name = Name.of("NCI SMDO - Villers St Paul");
        WESC wesc = new WESC(wescId, name, EMPTY_STOCK);
        assertThat(wesc.getId()).isEqualTo(wescId);
        assertThat(wesc.name).isEqualTo(name);
        assertThat(wesc.stock).isEqualTo(EMPTY_STOCK);
    }
    //endregion
}