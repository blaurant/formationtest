package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.Carrier;
import com.lb.formationtest.infrastructure.restapi.dto.BafRequest;

import java.time.LocalDate;

public class BafRequestAdaptor {

    private final BafRequest bafRequest;

    public BafRequestAdaptor(BafRequest bafRequest) {
        this.bafRequest = bafRequest;
    }

    public static BafRequestAdaptor toDomain(BafRequest bafRequest) {
        return new BafRequestAdaptor(bafRequest);
    }

    public Carrier.Id carrierId() {
        return Carrier.Id.fromString(bafRequest.carrierId);
    }

    public LocalDate fromDate() {
        return LocalDate.parse(bafRequest.fromDate, Codec.formatter);
    }

    public LocalDate toDate() {
        return LocalDate.parse(bafRequest.toDate, Codec.formatter);
    }
}
