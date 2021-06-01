package com.lb.formationtest.domain;

import com.lb.formationtest.domain.billing.Amount;

import java.util.Map;

public class TransportPrice {

    private static final Map<Chain, Amount> transportPrices = Map.of(
            Chain.CHAIN_1, Amount.of("230"),
            Chain.CHAIN_2, Amount.of("140"),
            Chain.CHAIN_3, Amount.of("300")
    );

    public static Amount priceFor(Chain chain) {
        return transportPrices.get(chain);
    }

}
