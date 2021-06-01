package com.lb.formationtest.infrastructure.restapi.dto;

public class Recycler {

    public final String recyclerId;
    public final String name;

    public Recycler(String recyclerId, String name) {
        this.recyclerId = recyclerId;
        this.name = name;
    }
}
