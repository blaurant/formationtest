package com.lb.formationtest.infrastructure.repositories;

import DDD.framework.NotYetImplementedException;
import com.lb.formationtest.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InMemoryTransactionRepo implements TransactionRepository {

    private Transactions transactions = new Transactions();

    @Override
    public Optional<Transaction> byId(Transaction.Id id) {
        return transactions.find(id);
    }

    @Override
    public void delete(Transaction transaction) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isPresent(Transaction.Id id) {
        throw new NotYetImplementedException();
    }

    @Override
    public void save(Transaction transaction) {
        transactions = transactions.add(transaction);
    }

    @Override
    public void deleteAll() {
        transactions = new Transactions();
    }

    @Override
    public List<Transaction> listAll() {
        return transactions.toList();
    }

    @Override
    public Transactions findByWESC(WESC.Id wescId) {
        return transactions.filter(transaction -> transaction.isChainWithWesc(wescId));
    }

    @Override
    public Transactions findCarrierIdAndBetweenDates(Carrier.Id carrierId, LocalDate from, LocalDate to) {
        return transactions
                .onlyCarrierId(carrierId)
                .onlyTransportBetween(from, to);
    }
}
