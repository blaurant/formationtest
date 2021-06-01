package com.lb.formationtest.infrastructure.restapi.dto;

import java.util.List;

public class Baf {

    public final List<BafItem> bafItem;
    public final String indexOfYear;
    public final String bafPrice;

    public Baf(List<BafItem> bafItem, String indexOfYear, String bafPrice) {
        this.bafItem = bafItem;
        this.indexOfYear = indexOfYear;
        this.bafPrice = bafPrice;
    }
}
