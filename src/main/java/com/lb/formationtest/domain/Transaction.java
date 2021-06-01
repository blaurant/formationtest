package com.lb.formationtest.domain;

import DDD.framework.ASetOf;
import DDD.framework.Entity;
import DDD.framework.EntityId;
import com.lb.formationtest.domain.billing.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static DDD.framework.Objects.checkSame;
import static DDD.framework.Objects.requireNotNull;
import static DDD.framework.Strings.requireLength;
import static com.lb.formationtest.domain.Actor.CARRIER;
import static com.lb.formationtest.domain.Actor.OPE;
import static com.lb.formationtest.domain.Reference.Family.*;
import static com.lb.formationtest.domain.Reference.generateFor;
import static com.lb.formationtest.domain.Transaction.State.*;
import static com.lb.formationtest.domain.Transaction.States.states;
import static java.time.LocalDate.now;

/**
 * Transaction is the main aggregate of the application, it handles removal request, transport and reception
 */
@DDD.Entity(aggregate = Transaction.class, rootAggregate = true)
public class Transaction extends Entity<Transaction.Id> {

    public static class Id extends EntityId<UUID> {

        public Id(UUID id) {
            super(id);
        }
        public static Id generate() {
            return new Id(UUID.randomUUID());
        }

        public static Id fromString(String idStr) {
            return new Id(UUID.fromString(requireLength(idStr, 36)));
        }

    }

    public enum State {
        CREATED,
        OPE_REMOVAL_REQUEST_CONFIRMED,
        CARRIER_TRANSPORT_CONFIRMED,
        TRANSPORTED,
        OPE_TRANSPORT_CONFIRMED,
        RECEIVED,
        OPE_RECEPTION_CONFIRMED;
    }
    static final States TRANSPORT_DONE_STATES = states(TRANSPORTED, OPE_TRANSPORT_CONFIRMED, RECEIVED, OPE_RECEPTION_CONFIRMED);

    private State state;
    public final Reference transacRef;
    public final Reference removalRequestRef;
    public final LocalDate creationDate;
    public final Quality quality;
    public final Quantity quantity;
    public final LocalDate availableDate;
    public final Chain chain;

    public final WESC.Id wescId;
    private Reference transportRef;
    private LocalDate collectDate = null;

    private LocalDate deliveryDate = null;
    private Reference receptionRef;
    private LocalDate receptionDate = null;
    private Quality receptionQuality = null;

    private Quantity receptionQuantity = null;

    private Documents documents = new Documents();

    //region commands

    public Transaction(Id id, Quality quality, Quantity quantity, LocalDate availableDate, Chain chain, WESC.Id wescId) {
        super(id);
        this.transacRef = generateFor(TRANSACTION);
        this.removalRequestRef = generateFor(REMOVAL_REQUEST);
        this.creationDate = now();
        this.quality = requireNotNull(quality);
        this.quantity = requireNotNull(quantity);
        this.availableDate = requireNotNull(availableDate);
        this.chain = requireNotNull(chain);
        this.wescId = requireNotNull(wescId);
        checkSame(quality, chain.quality, "Can not build a Transaction with different Quality fromDate the chain");
        checkSame(wescId, chain.wescId, "Can not build a Transaction with different WESC fromDate the chain");
        this.state = CREATED;
    }
    // TODO change toDate a real state engine

    public void validate(Actor actor) {
        if (actor.equals(OPE)) {
            checkState(states(CREATED, TRANSPORTED, RECEIVED), "validate by " + actor.name());
            if (isState(CREATED))
                this.state = OPE_REMOVAL_REQUEST_CONFIRMED;
            if (isState(TRANSPORTED))
                this.state = OPE_TRANSPORT_CONFIRMED;
            if (isState(RECEIVED))
                this.state = OPE_RECEPTION_CONFIRMED;
        }
        if (actor.equals(CARRIER)) {
            checkState(OPE_REMOVAL_REQUEST_CONFIRMED, "validate by " + actor.name());
            if (isState(OPE_REMOVAL_REQUEST_CONFIRMED))
                this.state = CARRIER_TRANSPORT_CONFIRMED;
        }
    }

    public void transport(LocalDate collectDate, LocalDate deliveryDate) {
        checkState(CARRIER_TRANSPORT_CONFIRMED, "transport");
        if (collectDate.isAfter(deliveryDate))
            throw new TransactionException("Can not update transport Transaction with collectDate after deliveryDate ");
        this.transportRef = generateFor(TRANSPORT);
        this.collectDate = collectDate;
        this.deliveryDate = deliveryDate;
        this.state = TRANSPORTED;
    }

    public void reception(LocalDate receptionDate, Quality receptionQuality, Quantity receptionQuantity) {
        checkState(OPE_TRANSPORT_CONFIRMED, "reception");
        if (receptionDate.isBefore(deliveryDate))
            throw new TransactionException("Can not update reception, Transaction with receptionDate before deliveryDate");
        this.receptionRef = generateFor(RECEPTION);
        this.receptionDate = receptionDate;
        this.receptionQuality = receptionQuality;
        this.receptionQuantity = receptionQuantity;
        this.state = RECEIVED;
    }
    public BafItem generateBafItem() {
        checkState(OPE_RECEPTION_CONFIRMED, "generate baf");
        return new BafItem(this);
    }

    public void attachDocuments(Documents documents) {
        this.documents = documents.addAll(documents);
    }
    //endregion

    public State currentState() {
        return state;
    }

    private boolean isState(State state) {
        return this.state.equals(state);
    }

    private void checkState(State state, String cmd) {
        if (!isState(state))
            ThrowCmdWhileInBadState(cmd);
    }

    private void checkState(States states, String cmd) {
        if (states.doNotContains(state))
            ThrowCmdWhileInBadState(cmd);
    }

    private void ThrowCmdWhileInBadState(String cmd) {
        throw new TransactionException("Can not operate cmd " + cmd + " while Transaction is in state " + state.name());
    }
    public boolean isChainWithWesc(WESC.Id wescId) {
        return chain.wescId.equals(wescId);
    }

    public boolean isChainWithCarrier(Carrier.Id carrierId) {
        return chain.carrierId.equals(carrierId);
    }

    private int getYearOfTransport() {
        checkState(TRANSPORT_DONE_STATES, "year of transport");
        return collectDate.getYear();
    }

    static class States extends ASetOf<State> {

        States(Set<State> set) {
            super(set);
        }

        States(State... States) {
            super(States);
        }

        static States states(State... s) {
            return new States(s);
        }

        @Override
        public <Sub extends ASetOf<State>> Sub cons(Set<State> newSet) {
            return (Sub) new States(newSet);
        }

    }

    public boolean isAfterTransport() {
        return TRANSPORT_DONE_STATES.contains(state);
    }

    public int getMonthOfTransport() {
        checkState(TRANSPORT_DONE_STATES, "month  of transport");
        return collectDate.getMonthValue();
    }

    public Amount calculateTransportPrice() {
        return PriceCalcService.calculateTransportPrice(
                ChainPrice.priceFor(chain),
                getTransportRefIndex(),
                getTranspotIndex());
    }

    public Index getTransportRefIndex() {
        return IndexService.refIndexOf(getYearOfTransport());
    }

    public Index getTranspotIndex() {
        return IndexService.indexOf(getYearOfTransport(), getMonthOfTransport());
    }

    public LocalDate getCollectDate() {
        return collectDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Reference getTransportRef() {
        return transportRef;
    }

    public Reference getReceptionRef() {
        return receptionRef;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public Quality getReceptionQuality() {
        return receptionQuality;
    }

    public Quantity getReceptionQuantity() {
        return receptionQuantity;
    }

    public Documents getDocuments() {
        return documents;
    }
}