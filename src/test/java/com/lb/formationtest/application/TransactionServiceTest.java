package com.lb.formationtest.application;

import com.lb.formationtest.domain.*;
import com.lb.formationtest.domain.Transaction.State;
import com.lb.formationtest.domain.billing.Amount;
import com.lb.formationtest.domain.billing.Baf;
import com.lb.formationtest.infrastructure.repositories.InMemoryTransactionRepo;
import com.lb.formationtest.infrastructure.repositories.StaticWESCRepo;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.lb.formationtest.application.TransactionServiceTest.*;
import static com.lb.formationtest.domain.Actor.CARRIER;
import static com.lb.formationtest.domain.Actor.OPE;
import static com.lb.formationtest.domain.Chain.CHAIN_1;
import static com.lb.formationtest.domain.Quality.PP_FD8;
import static com.lb.formationtest.domain.Transaction.State.*;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;


public class TransactionServiceTest extends ScenarioTest<GivenTransactionStatus, WhenTransactionAction, ThenTransactionStatus> {

    public static final Quality A_QUALITY = PP_FD8;
    public static final Quantity A_QUANTITY = Quantity.of("3.0");
    public static final WESC A_WESC = com.lb.formationtest.domain.WESC.WESC_1;
    public static final Chain A_CHAIN = CHAIN_1;

    @Before
    public void setUp() {
        Reference.clearAll();
    }

    @Test
    public void create_transaction() {
        given().a_quality(A_QUALITY)
                .and().a_quantity(A_QUANTITY)
                .and().an_available_date(now())
                .and().a_chain(A_CHAIN)
                .and().a_WESC(A_WESC);
        when().create_standard_transaction();
        then().transaction_is_in_state(CREATED)
                .and().reference_is("C1")
                .and().quality_is(A_QUALITY)
                .and().quantity_is(A_QUANTITY)
                .and().available_date_is(now())
                .and().chain_is(A_CHAIN)
                .and().wesc_is(A_WESC);
    }

    @Test
    public void validate_removal_request_by_Ope() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE);
        then().transaction_is_in_state(OPE_REMOVAL_REQUEST_CONFIRMED);
    }

    @Test
    public void validate_removal_request_by_Carrier() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER);
        then().transaction_is_in_state(CARRIER_TRANSPORT_CONFIRMED);
    }

    @Test
    public void transport() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER)
                .and().transport(now(), now());
        then().transaction_is_in_state(TRANSPORTED)
                .and().collect_date_is(now())
                .and().delivery_date_is(now());
    }

    @Test
    public void validate_transport_by_ope() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER)
                .and().transport(now(), now())
                .and().validate_by(OPE);
        then().transaction_is_in_state(OPE_TRANSPORT_CONFIRMED);
    }

    @Test
    public void recieve() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER)
                .and().transport(now(), now())
                .and().validate_by(OPE)
                .and().receive(now(), A_QUALITY, A_QUANTITY);
        then().transaction_is_in_state(RECEIVED)
                .and().reception_date_is(now())
                .and().reception_quality_is(A_QUALITY)
                .and().reception_quantity_is(A_QUANTITY);
    }

    @Test
    public void validate_reception_by_Ope() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER)
                .and().transport(now(), now())
                .and().validate_by(OPE)
                .and().receive(now(), A_QUALITY, A_QUANTITY)
                .and().validate_by(OPE);
        then().transaction_is_in_state(OPE_RECEPTION_CONFIRMED);
    }

    @Test
    public void baf() {
        given().a_standard_removal_request();
        when().create_standard_transaction()
                .and().validate_by(OPE)
                .and().validate_by(CARRIER)
                .and().transport(now(), now())
                .and().validate_by(OPE)
                .and().receive(now(), A_QUALITY, A_QUANTITY)
                .and().validate_by(OPE)
                .and().baf();
        then().baf_price_is(Amount.of("234.43"));
    }
}

class GivenTransactionStatus extends Stage<GivenTransactionStatus> {

    @ScenarioState
    private Quality quality;
    @ScenarioState
    private Quantity quantity;
    @ScenarioState
    private LocalDate availableDate;
    @ScenarioState
    private Chain chain;
    @ScenarioState
    private WESC wesc;

    public GivenTransactionStatus a_quality(Quality quality) {
        this.quality = quality;
        return self();
    }

    public GivenTransactionStatus a_quantity(Quantity quantity) {
        this.quantity = quantity;
        return self();
    }

    public GivenTransactionStatus an_available_date(LocalDate availableDate) {
        this.availableDate = availableDate;
        return self();
    }

    public GivenTransactionStatus a_chain(Chain chain) {
        this.chain = chain;
        return self();
    }

    public GivenTransactionStatus a_WESC(WESC wesc) {
        this.wesc = wesc;
        return self();
    }

    public GivenTransactionStatus a_standard_removal_request() {
        given().a_quality(A_QUALITY)
                .and().a_quantity(A_QUANTITY)
                .and().an_available_date(now())
                .and().a_chain(A_CHAIN)
                .and().a_WESC(A_WESC);
        return self();
    }
}

class WhenTransactionAction extends Stage<WhenTransactionAction> {

    @ProvidedScenarioState
    TransactionRepository transactionRepository = new InMemoryTransactionRepo();

    @ProvidedScenarioState
    TransactionService transactionService = new TransactionService(transactionRepository, new StaticWESCRepo());

    @ScenarioState
    private Quality quality;
    @ScenarioState
    private Quantity quantity;
    @ScenarioState
    private LocalDate availableDate;
    @ScenarioState
    private Chain chain;
    @ScenarioState
    private WESC wesc;

    @ScenarioState
    private Baf baf;

    @ScenarioState
    Transaction.Id transactionId;

    public WhenTransactionAction create_standard_transaction() {
        transactionId = Transaction.Id.generate();
        transactionService.createTransaction(transactionId, quality, quantity, availableDate, chain, wesc.getId());
        return self();
    }

    public WhenTransactionAction validate_by(Actor actor) {
        transactionService.validate(transactionId, actor);
        return self();
    }

    public WhenTransactionAction transport(LocalDate collectionDate, LocalDate deliveryDate) {
        transactionService.transport(transactionId, collectionDate, deliveryDate);
        return self();
    }

    public WhenTransactionAction receive(LocalDate receptionDate, Quality quality, Quantity quantity) {
        transactionService.receive(transactionId, receptionDate, quality, quantity);
        return self();
    }

    public WhenTransactionAction baf() {
        baf = transactionService.loadTransactionByCarrierIdAndBetweenDates(chain.carrierId, now(), now()).toBaf();
        return self();
    }
}


class ThenTransactionStatus extends Stage<ThenTransactionStatus> {

    @ProvidedScenarioState
    TransactionRepository transactionRepository;

    @ScenarioState
    Transaction.Id transactionId;

    @ScenarioState
    private Baf baf;

    public ThenTransactionStatus transaction_is_in_state(State state) {
        assertThat(load(transactionId).currentState()).isEqualTo(state);
        return self();
    }

    public ThenTransactionStatus reference_is(String refStr) {
        assertThat(refStr).isEqualTo(load(transactionId).transacRef.asString());
        return self();
    }

    public ThenTransactionStatus quality_is(Quality quality) {
        assertThat(load(transactionId).quality).isEqualTo(quality);
        return self();
    }

    public ThenTransactionStatus quantity_is(Quantity quantity) {
        assertThat(load(transactionId).quantity).isEqualTo(quantity);
        return self();
    }

    public ThenTransactionStatus available_date_is(LocalDate date) {
        assertThat(load(transactionId).availableDate).isEqualTo(date);
        return self();
    }

    public ThenTransactionStatus chain_is(Chain chain) {
        assertThat(load(transactionId).chain).isEqualTo(chain);
        return self();
    }

    public ThenTransactionStatus wesc_is(WESC wesc) {
        assertThat(load(transactionId).wescId).isEqualTo(wesc.getId());
        return self();
    }

    public ThenTransactionStatus collect_date_is(LocalDate date) {
        assertThat(load(transactionId).getCollectDate()).isEqualTo(date);
        return self();
    }

    public ThenTransactionStatus delivery_date_is(LocalDate date) {
        assertThat(load(transactionId).getDeliveryDate()).isEqualTo(date);
        return self();
    }

    public ThenTransactionStatus reception_date_is(LocalDate date) {
        assertThat(load(transactionId).getReceptionDate()).isEqualTo(date);
        return self();
    }

    public ThenTransactionStatus reception_quality_is(Quality quality) {
        assertThat(load(transactionId).getReceptionQuality()).isEqualTo(quality);
        return self();
    }

    public ThenTransactionStatus reception_quantity_is(Quantity quantity) {
        assertThat(load(transactionId).getReceptionQuantity()).isEqualTo(quantity);
        return self();
    }

    private Transaction load(Transaction.Id id) {
        return transactionRepository.byId(id).get();
    }

    public void baf_price_is(Amount amount) {
        assertThat(baf.price().truncateAtCents()).isEqualTo(amount);
    }
}

