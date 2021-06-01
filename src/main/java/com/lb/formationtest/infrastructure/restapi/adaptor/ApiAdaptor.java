package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.application.TransactionService;
import com.lb.formationtest.domain.WESC;
import com.lb.formationtest.domain.*;
import com.lb.formationtest.infrastructure.repositories.StaticCarrierRepo;
import com.lb.formationtest.infrastructure.repositories.StaticRecyclerRepo;
import com.lb.formationtest.infrastructure.repositories.StaticWESCRepo;
import com.lb.formationtest.infrastructure.restapi.dto.Carrier;
import com.lb.formationtest.infrastructure.restapi.dto.Chain;
import com.lb.formationtest.infrastructure.restapi.dto.Quality;
import com.lb.formationtest.infrastructure.restapi.dto.Recycler;
import com.lb.formationtest.infrastructure.restapi.dto.Transaction;
import com.lb.formationtest.infrastructure.restapi.dto.*;

import java.util.List;

import static com.lb.formationtest.infrastructure.restapi.adaptor.Codec.*;

/**
 * working only with dto in and dto out, this adaptor is a DDD's Anticorruption layer (ACL)
 */
@DDD.ACL
public class ApiAdaptor {

    private TransactionService transactionService;
    private CarrierRepository carrierRepository = new StaticCarrierRepo();
    private RecyclerRepository recyclerRepository = new StaticRecyclerRepo();
    private WESCRepository wescRepository = new StaticWESCRepo();

    public ApiAdaptor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction POSTRemovalRequest(RemovalRequest removalRequest) {
            RemovalRequestAdaptor adaptor = RemovalRequestAdaptor.toDomain(removalRequest);
            com.lb.formationtest.domain.Transaction transaction = transactionService.createTransaction(
                    adaptor.transactionId(),
                    adaptor.quality(),
                    adaptor.quantity(),
                    adaptor.availableDate(),
                    new com.lb.formationtest.domain.Chain(
                            adaptor.chainQuality(),
                            adaptor.ChainCarrierId(),
                            adaptor.chainRecyclerId(),
                            adaptor.chainWescId()),
                    adaptor.wescId());
            return toTransactionDto.apply(transaction);
    }

    public Transaction POSTValidation(String transactionIdStr, Actor actor) {
        com.lb.formationtest.domain.Transaction transaction = transactionService.validate(
                toTransacId(transactionIdStr),
                actor);
        return toTransactionDto.apply(transaction);
    }

    public Transaction POSTUpload(String transactionIdStr, UploadDoc uploadDocDto) {
        com.lb.formationtest.domain.Transaction transaction = transactionService.attachDocument(
                toTransacId(transactionIdStr),
                UploadDocAdaptor.toDomain(uploadDocDto).documents());
        return toTransactionDto.apply(transaction);
    }

    public Transaction POSTTransport(Transport transport) {
        TransportAdaptor adaptor = TransportAdaptor.toDomain(transport);
        com.lb.formationtest.domain.Transaction transaction = transactionService.transport(
                adaptor.transactionId(),
                adaptor.collectDate(),
                adaptor.deliveryDate());
        return toTransactionDto.apply(transaction);
    }

    public Object POSTReception(Reception reception) {
        ReceptionAdaptor adaptor = ReceptionAdaptor.toDomain(reception);
            com.lb.formationtest.domain.Transaction transaction = transactionService.receive(
                    adaptor.transactionId(),
                    adaptor.receptionDate(),
                    adaptor.receptionQuality(),
                    adaptor.receptionQuantity());
        return toTransactionDto.apply(transaction);
    }

    private com.lb.formationtest.domain.Transaction.Id toTransacId(String transactionIdStr) {
        return com.lb.formationtest.domain.Transaction.Id.fromString(transactionIdStr);
    }

    public BafItem GETBAFItemForTransaction(String transactionIdStr) {
        com.lb.formationtest.domain.billing.BafItem bafItem = transactionService
                .loadTransactionById(toTransacId(transactionIdStr))
                .generateBafItem();
        return toBafItemDto.apply(bafItem);
    }

    public Baf POSTBAFForCarrier(BafRequest bafRequest) {
        BafRequestAdaptor adaptor = BafRequestAdaptor.toDomain(bafRequest);
        com.lb.formationtest.domain.billing.Baf baf = transactionService.loadTransactionByCarrierIdAndBetweenDates(
                    adaptor.carrierId(),
                    adaptor.fromDate(),
                    adaptor.toDate())
                .toBaf();
        return toBafItemListDto.apply(baf);
    }

    public void DELETEAllTransaction() {
        transactionService.deleteAll();
    }

    public List<Carrier> GETAllCarrier() {
        Carriers carriers = carrierRepository.findAll();
        return toCarrierListDto.apply(carriers);
    }

    public List<Recycler> GETALLRecycler() {
        Recyclers recyclers = recyclerRepository.findAll();
        return toRecyclerListDto.apply(recyclers);
    }

    public List<Quality> GETAllQuality() {
        return toQualityListDto();
    }

    public List<Chain> GETChainByWESC(WESC.Id wescId) {
        Chains chains = Chains.CHAINS.filterOnWESC(wescId);
        return toChainListDto.apply(chains);
    }

    public Transaction GETTransaction(String transactionIdStr) {
        com.lb.formationtest.domain.Transaction transaction = transactionService.loadTransactionById(toTransacId(transactionIdStr));
        return toTransactionDto.apply(transaction);
    }

    public Object GETWesc(WESC.Id wescId) {
            WESC wesc = loadWESC(wescId);
            return toWESCDto.apply(wesc);
    }

    public List<Transaction> GETTransactionByWesc(WESC.Id wescId) {
        Transactions transactions = transactionService.findByWESC(wescId);
        return toTransactionListDto.apply(transactions);
    }

    private WESC loadWESC(WESC.Id wescId) {
        return wescRepository.byId(wescId).orElseThrow(() ->
        new WESCNotFoundException("WESC with " + wescId.asString() + " not found"));
    }

}
