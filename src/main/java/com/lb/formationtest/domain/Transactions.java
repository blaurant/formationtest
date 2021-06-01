package com.lb.formationtest.domain;

import DDD.framework.ASetOf;
import com.lb.formationtest.domain.billing.Baf;
import com.lb.formationtest.domain.billing.BafItems;
import com.lb.formationtest.domain.billing.Index;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Transactions extends ASetOf<Transaction> {

    public Transactions(Set<Transaction> set) {
        super(set);
    }

    public Transactions(Transaction... Transactions) {
        super(Transactions);
    }

    @Override
    public <Sub extends ASetOf<Transaction>> Sub cons(Set<Transaction> newSet) {
        return (Sub) new Transactions(newSet);
    }

    public Optional<Transaction> find(Transaction.Id id) {
        return filter(transaction -> transaction.getId().equals(id)).unique();
    }

    public Transactions onlyCarrierId(Carrier.Id carrierId) {
        return filter(transaction -> transaction.isChainWithCarrier(carrierId));
    }

    public Transactions onlyTransportBetween(LocalDate from, LocalDate to) {
        return filter(transaction ->
                transaction.isAfterTransport() &&
                (transaction.getCollectDate().isAfter(from) || transaction.getCollectDate().isEqual(from)) &&
                (transaction.getDeliveryDate().isBefore(to) || transaction.getDeliveryDate().isEqual(to)));
    }

    public Baf toBaf() {
        return new Baf(
                new BafItems(onlyAfterTransport().map(Transaction::generateBafItem)),
                getTransportRefIndex());
    }

    private Index getTransportRefIndex() {
        Optional<Transaction> trOpt = first();
        if (trOpt.isPresent())
            return trOpt.get().getTransportRefIndex();
        else
            return Index.NAI;
    }

    public Transactions onlyAfterTransport() {
        return filter(transaction -> transaction.isAfterTransport());
    }
}
