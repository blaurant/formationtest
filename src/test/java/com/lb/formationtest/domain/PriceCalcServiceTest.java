package com.lb.formationtest.domain;

import com.lb.formationtest.domain.billing.Amount;
import com.lb.formationtest.domain.billing.Index;
import com.lb.formationtest.domain.billing.PriceCalcService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalcServiceTest {

    @Test
    public void calculateTransportPrice() {
        assertThat(PriceCalcService.calculateTransportPrice(
                Amount.of("230"), Index.of("163.48"), Index.of("178")).truncateAtCents())
                .isEqualTo(Amount.of("234.43"));
    }
}
