package com.lb.formationtest.infrastructure.restapi.dto;

public class StockPile {
    public final String quality;
    public final String quantity;

    public StockPile(String quality, String quantity) {
        this.quality = quality;
        this.quantity = quantity;
    }
}
