package com.lb.formationtest.domain;

import DDD.framework.Objects;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NameTest {

    //region Domain invariant test (building integrity)
    @Test
    public void integrityConstraints_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Name(null))
                .withMessage(Objects.CAN_NOT_BE_NULL);
    }
    @Test
    public void integrityConstraints_name_too_short() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Name("aa"))
                .withMessage(Name.NB_CHAR_MAX_ERR);
    }

    @Test
    public void nominal_construction() {
        String name = "FDTT";
        assertThat(new Name(name).value).isEqualTo(name);
    }
    //endregion
}