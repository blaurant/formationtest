package com.lb.formationtest.infrastructure.restapi.dto;


import java.util.List;

public class Transaction {

    public final String transactionId;
    public final String transactionRef;
    public final String state;

    public final String removalRequestRef;
    public final String creationDate;
    public final String quality;
    public final String quantity;
    public final String availableDate;
    public final Chain chain;
    public final String wescId;

    public final String transportRef;
    public final String collectDate;
    public final String deliveryDate;

    public final String receptionRef;
    public final String receptionDate;
    public final String receptionQuality;
    public final String receptionQuantity;

    public final List<Document> documents;

    public Transaction(String transactionId, String transactionRef, String state, String removalRequestRef, String creationDate, String quality, String quantity, String availableDate, Chain chain, String wescId, String transportRef, String collectDate, String deliveryDate, String receptionRef, String receptionDate, String receptionQuality, String receptionQuantity, List<Document> documents) {
        this.transactionId = transactionId;
        this.transactionRef = transactionRef;
        this.state = state;
        this.removalRequestRef = removalRequestRef;
        this.creationDate = creationDate;
        this.quality = quality;
        this.quantity = quantity;
        this.availableDate = availableDate;
        this.chain = chain;
        this.wescId = wescId;
        this.transportRef = transportRef;
        this.collectDate = collectDate;
        this.deliveryDate = deliveryDate;
        this.receptionRef = receptionRef;
        this.receptionDate = receptionDate;
        this.receptionQuality = receptionQuality;
        this.receptionQuantity = receptionQuantity;
        this.documents = documents;
    }
}
