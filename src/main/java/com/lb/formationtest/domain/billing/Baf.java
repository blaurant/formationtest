package com.lb.formationtest.domain.billing;

import DDD.ValueObject;

import java.util.Objects;

import static DDD.framework.Objects.requireNotNull;

@ValueObject
public class Baf {

    public final BafItems bafItems;
    public final Index RefIndex;

    public Baf(BafItems bafItems, Index refIndex) {
        this.bafItems = requireNotNull(bafItems);
        RefIndex = refIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baf baf = (Baf) o;
        return bafItems.equals(baf.bafItems) &&
                RefIndex.equals(baf.RefIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bafItems, RefIndex);
    }

    public Amount price() {
        return bafItems.sum();
    }

    public boolean isEmpty() {
        return bafItems.isEmpty();
    }
}
