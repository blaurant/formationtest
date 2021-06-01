package com.lb.formationtest.domain;

import DDD.framework.Entity;
import DDD.framework.EntityId;
import DDD.framework.Objects;

import java.util.UUID;

import static DDD.framework.Strings.requireLength;


/**
 * A Waste Extra Sorting Center ("Centre de surtri" in french) is a RootEntity(DDD).
 * So its also an Aggregate and it handle it's stock
 * No lifecycle for the moment
 */
@DDD.Entity(aggregate = WESC.class)
public class WESC extends Entity<WESC.Id> {

    public static final WESC.Id WESC_ID_1 = Id.fromString("4f398ca5-5f55-4671-b148-61ca73d5a70c");
    public static final WESC.Id WESC_ID_2 = Id.fromString("e23a925d-0f37-442e-a1fd-75ee6c754cd4");

    public static final WESC WESC_1 = new WESC(WESC_ID_1, Name.of("NCI SMDO - Villers St Paul"),
            new Stock(
                    new StockPile(Quality.PET_BF_CLAIR, Quantity.of("12.230")),
                    new StockPile(Quality.PET_BF_COLORE, Quantity.of("11.1")),
                    new StockPile(Quality.PET_BF_COLORE_ET_OPAQUE, Quantity.of("9.07")),
                    new StockPile(Quality.PP_FD8, Quantity.of("4.20")),
                    new StockPile(Quality.PP_FD8, Quantity.of("20.234")),
                    new StockPile(Quality.PS, Quantity.of("3.4"))
            ));
    public static final WESC WESC_2 = new WESC(WESC_ID_2, Name.of("RHONE RECYCLAGE - Paprec"),
            new Stock(
                    new StockPile(Quality.PET_BF_CLAIR, Quantity.of("2.1")),
                    new StockPile(Quality.PET_BF_COLORE, Quantity.of("15.345")),
                    new StockPile(Quality.PP_FD8, Quantity.of("10.34")),
                    new StockPile(Quality.PS, Quantity.of("4.44"))
            ));

    public static class Id extends EntityId<UUID> {
        public Id(UUID id) {
            super(id);
        }

        public static Id generate() {
            return new Id(UUID.randomUUID());
        }

        public static Id fromString(String str) {
            return new Id(UUID.fromString(requireLength(str, 36)));
        }
    }

    public final Name name;
    public final Stock stock;

    public WESC(Id id, Name name, Stock stock) {
        super(id);
        this.name = Objects.requireNotNull(name);
        this.stock = Objects.requireNotNull(stock);
    }
}

