package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.Transaction;
import com.lb.formationtest.infrastructure.restapi.dto.Transport;

import java.time.LocalDate;


public class TransportAdaptor {

    public final Transport transportDto;

    public TransportAdaptor(Transport transportDto) {
        this.transportDto = transportDto;
    }
    public static TransportAdaptor toDomain(Transport transport) {
        return new TransportAdaptor(transport);
    }

    public Transaction.Id transactionId() {
        return Transaction.Id.fromString(transportDto.transactionId);
    }

    public LocalDate collectDate() {
        return LocalDate.parse(transportDto.collectDate, Codec.formatter);
    }

    public LocalDate deliveryDate() {
        return LocalDate.parse(transportDto.deliveryDate, Codec.formatter);
    }
}
