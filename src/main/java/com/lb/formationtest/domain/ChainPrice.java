package com.lb.formationtest.domain;

import com.lb.formationtest.domain.billing.Amount;

import java.util.Map;

public class ChainPrice {

    private static final Map<Chain, Amount> transportPrices = Map.of(
            Chain.CHAIN_1, Amount.of("230"),
            Chain.CHAIN_2, Amount.of("140"),
            Chain.CHAIN_3, Amount.of("150"),
            Chain.CHAIN_4, Amount.of("200"),
            Chain.CHAIN_5, Amount.of("300"),
            Chain.CHAIN_6, Amount.of("250"),
            Chain.CHAIN_7, Amount.of("220"),
            Chain.CHAIN_8, Amount.of("280")
    );

    public static Amount priceFor(Chain chain) {
        return transportPrices.get(chain);
    }

}
