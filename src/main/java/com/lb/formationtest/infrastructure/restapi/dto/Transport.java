package com.lb.formationtest.infrastructure.restapi.dto;

public class Transport {

    public final String transactionId;
    public final String collectDate;
    public final String deliveryDate;

    public Transport(String transactionId, String collectDate, String deliveryDate) {
        this.transactionId = transactionId;
        this.collectDate = collectDate;
        this.deliveryDate = deliveryDate;
    }

}
