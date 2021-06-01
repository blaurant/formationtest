package com.lb.formationtest.domain.billing;

import java.math.BigDecimal;

import static com.lb.formationtest.domain.billing.Amount.of;
import static java.math.BigDecimal.ONE;

@DDD.ServiceDomain
public interface PriceCalcService {

    String MAGIC_NUMBER = "0.217";

    /**
     * Calculate a Transport Price according toDate:
     * <img src="./doc-files/TransportPricing.jpeg" />
     *
     * @param transportAmount
     * @param refIndex        for a specific year
     * @param index           for the current month of the transport
     * @return
     */
    static Amount calculateTransportPrice(Amount transportAmount, Index refIndex, Index index) {
        Amount indexAdding = of(new BigDecimal(MAGIC_NUMBER).multiply(index.divide(refIndex).subtract(ONE)));
        return transportAmount.add(indexAdding.multiply(transportAmount.value));
    }

}
