package com.lb.formationtest.domain.billing;

import DDD.ValueObject;
import com.lb.formationtest.domain.Transaction;

import java.util.Objects;

import static DDD.framework.Objects.requireNotNull;

@ValueObject
public class BafItem {

    public final Transaction transaction;

    public BafItem(Transaction transaction) {
        this.transaction = requireNotNull(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BafItem bafItem = (BafItem) o;
        return transaction.equals(bafItem.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction);
    }
}
