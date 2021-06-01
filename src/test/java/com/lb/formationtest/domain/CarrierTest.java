package com.lb.formationtest.domain;

import DDD.framework.EntityId;
import DDD.framework.Objects;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CarrierTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null_id() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Carrier(null, null))
                .withMessage(EntityId.ID_IS_NULL);
    }

    @Test
    public void integrityConstraints_null_name() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Carrier(Carrier.Id.generate(), null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }
    @Test
    public void nominal_construction() {
        Carrier.Id carrierId = Carrier.Id.generate();
        Name name = Name.of("a name");
        Carrier carrier = new Carrier(carrierId, name);
        assertThat(carrier.getId()).isEqualTo(carrierId);
        assertThat(carrier.name).isEqualTo(name);
    }
    //endregion
}