package com.lb.formationtest.domain;

import DDD.ValueObject;
import DDD.framework.Objects;

@ValueObject
public class Stock {

    public static final Stock EMPTY_STOCK = new Stock();

    private StockPiles stockPiles;

    public Stock(StockPiles stockPiles) {
        this.stockPiles = Objects.requireNotNull(stockPiles);
    }

    public Stock(StockPile... stockPiles) {
        this.stockPiles = new StockPiles(Objects.requireNotNull(stockPiles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return stockPiles.equals(stock.stockPiles);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(stockPiles);
    }

    public StockPiles getStockPiles() {
        return stockPiles;
    }
}
