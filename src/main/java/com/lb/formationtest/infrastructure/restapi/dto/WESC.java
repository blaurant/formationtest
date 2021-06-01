package com.lb.formationtest.infrastructure.restapi.dto;

import java.util.List;

public class WESC {
    public final String WESCId;
    public final String name;
    public final List<StockPile> stockPileList;

    public WESC(String wescId, String name, List stockPileDtoList) {
        this.WESCId = wescId;
        this.name = name;
        this.stockPileList = stockPileDtoList;
    }
}

