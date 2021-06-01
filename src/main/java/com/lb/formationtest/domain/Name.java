package com.lb.formationtest.domain;

import DDD.ValueObject;
import DDD.framework.SimpleValueObject;
import DDD.framework.Strings;

@ValueObject
public class Name extends SimpleValueObject<String> {

    public static final String NB_CHAR_MAX_ERR = "Length of Name must be more then 3 digit";

    public Name(String value) {
        super(value);
        Strings.requireMinLength(value, 3, NB_CHAR_MAX_ERR);
    }

    public static Name of(String str) {
        return new Name(str);
    }

    public String asString() {
        return value;
    }
}

