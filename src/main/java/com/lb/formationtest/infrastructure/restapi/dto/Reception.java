package com.lb.formationtest.infrastructure.restapi.dto;

public class Reception {

    public final String transactionId;
    public final String receptionDate;
    public final String receptionQuality;
    public final String receptionQuantity;

    public Reception(String transactionId, String receptionDate, String receptionQuality, String receptionQuantity) {
        this.transactionId = transactionId;
        this.receptionDate = receptionDate;
        this.receptionQuality = receptionQuality;
        this.receptionQuantity = receptionQuantity;
    }
}
