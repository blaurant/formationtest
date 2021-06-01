package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.*;
import com.lb.formationtest.infrastructure.restapi.dto.RemovalRequest;

import java.time.LocalDate;

public class RemovalRequestAdaptor {

    private final RemovalRequest removalRequestDto;

    private RemovalRequestAdaptor(RemovalRequest removalRequestDto) {
        this.removalRequestDto = removalRequestDto;
    }

    public static RemovalRequestAdaptor toDomain(RemovalRequest removalRequestDto) {
        return new RemovalRequestAdaptor(removalRequestDto);
    }

    public Transaction.Id transactionId() {
        return Transaction.Id.fromString(removalRequestDto.transactionId);
    }

    public Quality quality() {
        return Quality.valueOf(removalRequestDto.quality);
    }

    public Quantity quantity() {
        return Quantity.of(removalRequestDto.quantity);
    }

    public LocalDate availableDate() {
        return LocalDate.parse(removalRequestDto.availableDate, Codec.formatter);
    }

    public Quality chainQuality() {
        return Quality.valueOf(removalRequestDto.chain.quality);
    }

    public Carrier.Id ChainCarrierId() {
        return Carrier.Id.fromString(removalRequestDto.chain.carrierId);
    }

    public Recycler.Id chainRecyclerId() {
        return Recycler.Id.fromString(removalRequestDto.chain.recyclerId);
    }

    public WESC.Id chainWescId() {
        return WESC.Id.fromString(removalRequestDto.chain.wescId);
    }

    public WESC.Id wescId() {
        return WESC.Id.fromString(removalRequestDto.wescId);
    }
}
