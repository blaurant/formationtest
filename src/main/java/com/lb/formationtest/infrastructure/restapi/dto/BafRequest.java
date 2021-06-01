package com.lb.formationtest.infrastructure.restapi.dto;

public class BafRequest {

    public final String carrierId;
    public final String fromDate;
    public final String toDate;

    public BafRequest(String carrierId, String fromDate, String toDate) {
        this.carrierId = carrierId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
