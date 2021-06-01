package com.lb.formationtest.infrastructure.restapi.dto;

public class Quality {

    public final String name;
    public final String code;
    public final String wording;

    public Quality(String name, String code, String wording) {
        this.name = name;
        this.code = code;
        this.wording = wording;
    }
}
