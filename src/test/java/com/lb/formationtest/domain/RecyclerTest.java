package com.lb.formationtest.domain;

import DDD.framework.EntityId;
import DDD.framework.Objects;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RecyclerTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null_id() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Recycler(null, null))
                .withMessage(EntityId.ID_IS_NULL);
    }

    @Test
    public void integrityConstraints_null_name() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Recycler(Recycler.Id.generate(), null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }
    @Test
    public void nominal_construction() {
        Recycler.Id carrierId = Recycler.Id.generate();
        Name name = Name.of("a name");
        Recycler carrier = new Recycler(carrierId, name);
        assertThat(carrier.getId()).isEqualTo(carrierId);
        assertThat(carrier.name).isEqualTo(name);
    }
    //endregion
}