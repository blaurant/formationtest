package com.lb.formationtest.domain;

import DDD.ValueObject;

import static DDD.framework.Objects.requireNotNull;

@ValueObject
public class StockPile {

    public final Quality quality;
    public final Quantity quantity;

    public StockPile(Quality quality, Quantity quantity) {
        this.quality = requireNotNull(quality);
        this.quantity = requireNotNull(quantity);
    }
}
