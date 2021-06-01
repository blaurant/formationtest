package com.lb.formationtest.infrastructure.restapi.dto;

public class BafItem {

    public final Transaction transaction;
    public final String price;
    public final String collectDate;
    public final String deliveryDate;
    public final String indexOfMonth;

    public BafItem(Transaction transaction, String price, String collectDate, String deliveryDate, String indexOfMonth) {
        this.transaction = transaction;
        this.price = price;
        this.collectDate = collectDate;
        this.deliveryDate = deliveryDate;
        this.indexOfMonth = indexOfMonth;
    }
}
