package com.lb.formationtest.infrastructure.restapi.dto;

public class RemovalRequest {

    public final String transactionId;
    public final String quality;
    public final String quantity;
    public final String availableDate;
    public final Chain chain;
    public final String wescId;

    public RemovalRequest(String transactionId, String quality, String quantity, String availableDate, Chain chain, String wescId) {
        this.transactionId = transactionId;
        this.quality = quality;
        this.quantity = quantity;
        this.availableDate = availableDate;
        this.chain = chain;
        this.wescId = wescId;
    }
}
