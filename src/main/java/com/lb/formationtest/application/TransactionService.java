package com.lb.formationtest.application;

import com.lb.formationtest.domain.*;

import java.time.LocalDate;

public class TransactionService {

    private TransactionRepository transactionRepo;
    private WESCRepository wescRepository;

    public TransactionService(TransactionRepository transactionRepo, WESCRepository wescRepository) {
        this.transactionRepo = transactionRepo;
        this.wescRepository = wescRepository;
    }

    public Transaction createTransaction(Transaction.Id id, Quality quality, Quantity quantity, LocalDate availableDate, Chain chain, WESC.Id wescId) {
        checkNoTransactionWithId(id);
        checkWescWithId(wescId);
        checkWescWithId(chain.wescId);
        Transaction transaction = new Transaction(id, quality, quantity, availableDate, chain, wescId);
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction transport(Transaction.Id id, LocalDate collectDate, LocalDate deliveryDate) {
        checkTransactionWithId(id);
        Transaction transaction = loadTransactionById(id);
        transaction.transport(collectDate, deliveryDate);
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction receive(Transaction.Id id, LocalDate receptionDate, Quality receptionQuality, Quantity receptionQuantity) {
        checkTransactionWithId(id);
        Transaction transaction = loadTransactionById(id);
        transaction.reception(receptionDate, receptionQuality, receptionQuantity);
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction validate(Transaction.Id id, Actor actor) {
        checkTransactionWithId(id);
        Transaction transaction = loadTransactionById(id);
        transaction.validate(actor);
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction attachDocument(Transaction.Id id, Documents documents) {
        checkTransactionWithId(id);
        Transaction transaction = loadTransactionById(id);
        transaction.attachDocuments(documents);
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction loadTransactionById(Transaction.Id transactionId) {
        return transactionRepo.byId(transactionId).orElseThrow(() ->
                new ApplicationException("Transaction with id " + transactionId.asString() + " not found"));
    }

    private void checkNoTransactionWithId(Transaction.Id id) {
        if (transactionRepo.byId(id).isPresent())
            throw new ApplicationException("Transaction with Id" + id.asString() + " already exists");
    }

    private void checkTransactionWithId(Transaction.Id id) {
        if (transactionRepo.byId(id).isEmpty())
            throw new TransactionException("Transaction with Id" + id.asString() + " doesnt exists");
    }

    private void checkWescWithId(WESC.Id wescId) {
        if (wescRepository.byId(wescId).isEmpty())
            throw new TransactionException("WESC " + wescId + " does not exist");
    }

    public Transactions loadTransactionByCarrierIdAndBetweenDates(Carrier.Id carrierId, LocalDate from, LocalDate to) {
        return transactionRepo.findCarrierIdAndBetweenDates(carrierId, from, to);
    }

    public void deleteAll() {
        transactionRepo.deleteAll();
    }

    public Transactions findByWESC(WESC.Id wescId) {
        return transactionRepo.findByWESC(wescId);
    }
}
