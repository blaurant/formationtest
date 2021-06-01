package com.lb.formationtest.infrastructure.restapi.dto;

public class Chain {

    public final String wescId;
    public final String quality;
    public final String carrierId;
    public final String recyclerId;

    public Chain(String wescId, String quality, String carrierId, String recyclerId) {
        this.wescId = wescId;
        this.quality = quality;
        this.carrierId = carrierId;
        this.recyclerId = recyclerId;
    }
}
