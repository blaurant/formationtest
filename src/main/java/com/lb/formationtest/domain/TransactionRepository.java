package com.lb.formationtest.domain;

import DDD.framework.repository.FullRepo;

import java.time.LocalDate;

public interface TransactionRepository extends FullRepo<Transaction.Id, Transaction> {
    Transactions findByWESC(WESC.Id wescId);

    Transactions findCarrierIdAndBetweenDates(Carrier.Id carrierId, LocalDate from, LocalDate to);
}
