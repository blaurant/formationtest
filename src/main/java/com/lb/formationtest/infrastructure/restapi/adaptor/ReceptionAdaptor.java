package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.Quality;
import com.lb.formationtest.domain.Quantity;
import com.lb.formationtest.domain.Transaction;
import com.lb.formationtest.infrastructure.restapi.dto.Reception;

import java.time.LocalDate;

public class ReceptionAdaptor {

    private final Reception receptionDto;

    private ReceptionAdaptor(Reception receptionDto) {
        this.receptionDto = receptionDto;
    }

    public static ReceptionAdaptor toDomain(Reception reception) {
        return new ReceptionAdaptor(reception);
    }

    public Transaction.Id transactionId() {
        return Transaction.Id.fromString(receptionDto.transactionId);
    }

    public LocalDate receptionDate() {
        return LocalDate.parse(receptionDto.receptionDate, Codec.formatter);
    }

    public Quality receptionQuality() {
        return Quality.valueOf(receptionDto.receptionQuality);
    }

    public Quantity receptionQuantity() {
        return Quantity.of(receptionDto.receptionQuantity);
    }
}
