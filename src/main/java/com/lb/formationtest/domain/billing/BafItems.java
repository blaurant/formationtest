package com.lb.formationtest.domain.billing;

import DDD.framework.ASetOf;

import java.util.Set;

@DDD.ValueObject
public class BafItems extends ASetOf<BafItem> {

    public BafItems(Set<BafItem> set) {
        super(set);
    }

    public BafItems(BafItem... BafItems) {
        super(BafItems);
    }

    @Override
    public <Sub extends ASetOf<BafItem>> Sub cons(Set<BafItem> newSet) {
        return (Sub) new BafItems(newSet);
    }

    public Amount sum() {
        return stream().map(bafItem -> bafItem.transaction.calculateTransportPrice())
                .reduce(Amount.ZERO, (amount, amount2) -> amount.add(amount2));
    }
}

   