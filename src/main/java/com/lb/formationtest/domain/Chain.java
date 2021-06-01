package com.lb.formationtest.domain;

import DDD.ValueObject;

import java.util.Objects;

import static DDD.framework.Objects.requireNotNull;

@ValueObject
public class Chain {

    public static final Chain CHAIN_1 = new Chain(Quality.PP_FD8, Carrier.CARRIER_ID_1, Recycler.RECYCLER_ID_1, WESC.WESC_ID_1);
    public static final Chain CHAIN_2 = new Chain(Quality.PS    , Carrier.CARRIER_ID_1, Recycler.RECYCLER_ID_2, WESC.WESC_ID_1);
    public static final Chain CHAIN_3 = new Chain(Quality.PS    , Carrier.CARRIER_ID_2, Recycler.RECYCLER_ID_1, WESC.WESC_ID_1);
    public static final Chain CHAIN_4 = new Chain(Quality.PS    , Carrier.CARRIER_ID_2, Recycler.RECYCLER_ID_2, WESC.WESC_ID_1);

    public static final Chain CHAIN_5 = new Chain(Quality.PS    , Carrier.CARRIER_ID_1, Recycler.RECYCLER_ID_1, WESC.WESC_ID_2);
    public static final Chain CHAIN_6 = new Chain(Quality.PS    , Carrier.CARRIER_ID_1, Recycler.RECYCLER_ID_2, WESC.WESC_ID_2);
    public static final Chain CHAIN_7 = new Chain(Quality.PS    , Carrier.CARRIER_ID_2, Recycler.RECYCLER_ID_1, WESC.WESC_ID_2);
    public static final Chain CHAIN_8 = new Chain(Quality.PS    , Carrier.CARRIER_ID_2, Recycler.RECYCLER_ID_2, WESC.WESC_ID_2);

    public final Quality quality;
    public final Carrier.Id carrierId;
    public final Recycler.Id recyclerId;
    public final WESC.Id wescId;

    public Chain(Quality quality, Carrier.Id carrierId, Recycler.Id recyclerId, WESC.Id wescId) {
        this.quality = requireNotNull(quality);
        this.carrierId = requireNotNull(carrierId);
        this.recyclerId = requireNotNull(recyclerId);
        this.wescId = requireNotNull(wescId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chain chain = (Chain) o;
        return quality == chain.quality &&
                carrierId.equals(chain.carrierId) &&
                recyclerId.equals(chain.recyclerId) &&
                wescId.equals(chain.wescId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quality, carrierId, recyclerId, wescId);
    }

    @Override
    public String toString() {
        return "{" +
                "quality=" + quality +
                ", carrierId=" + carrierId +
                ", recyclerId=" + recyclerId +
                ", wescId=" + wescId +
                '}';
    }
}
