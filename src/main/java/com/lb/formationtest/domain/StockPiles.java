package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Set;

public class StockPiles extends ASetOf<StockPile> {

    public StockPiles(Set<StockPile> set) {
        super(set);
    }

    public StockPiles(StockPile... StockPiles) {
        super(StockPiles);
    }

    @Override
    public <Sub extends ASetOf<StockPile>> Sub cons(Set<StockPile> newSet) {
        return (Sub) new StockPiles(newSet);
    }

}

   