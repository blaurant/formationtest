package com.lb.formationtest.domain;

import DDD.framework.SimpleValueObject;
import DDD.framework.Strings;

@DDD.ValueObject
public class Document extends SimpleValueObject<String> {

    public Document(String value) {
        super(value);
        Strings.requireNotEmpty(value, "Document url is empty");
    }
}
