package com.lb.formationtest.domain;

import org.junit.Before;
import org.junit.Test;

import static DDD.framework.Objects.CAN_NOT_BE_NULL;
import static com.lb.formationtest.domain.Reference.Family.TRANSPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ReferenceTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null_family() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Reference(null, null))
                .withMessage(CAN_NOT_BE_NULL);
    }

    @Test
    public void integrityConstraints_null_counter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Reference(TRANSPORT, null))
                .withMessage(CAN_NOT_BE_NULL);
    }
    //endregion


    @Before
    public void setUp() throws Exception {
        Reference.clearAll();
    }

    @Test
    public void incrRef() {
        Reference reference = Reference.generateFor(TRANSPORT);
        assertThat(Reference.generateFor(TRANSPORT)).isEqualTo(new Reference(TRANSPORT, 2L));
    }

    @Test
    public void asString() {
        assertThat(Reference.generateFor(TRANSPORT).asString()).isEqualTo("T1");
    }

    @Test
    public void generateFor() {
        assertThat(Reference.generateFor(TRANSPORT)).isEqualTo(new Reference(TRANSPORT, 1L));
    }

    @Test
    public void equals() {
        assertThat(new Reference(TRANSPORT, 1L).equals(Reference.generateFor(TRANSPORT))).isTrue();
    }
}