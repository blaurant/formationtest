package com.lb.formationtest.domain;

import DDD.framework.Entity;
import DDD.framework.EntityId;

import java.util.UUID;

import static DDD.framework.Objects.requireNotNull;
import static DDD.framework.Strings.requireLength;

@DDD.Entity(aggregate = Carrier.class)
public class Carrier extends Entity<Carrier.Id> {

    public static final Id CARRIER_ID_1 = Id.fromString("bb9c3fd9-d7f7-4baa-b34a-3fc00fbe40de");
    public static final Carrier CARRIER_1 = new Carrier(CARRIER_ID_1, Name.of("MAUFFREY 88"));

    public static final Id CARRIER_ID_2 = Id.fromString("083c912b-6851-4fcb-8906-4045d159f5e8");
    public static final Carrier CARRIER_2 = new Carrier(CARRIER_ID_2, Name.of("MALHERBE NORD"));


    public static class Id extends EntityId<UUID> {
        public Id(UUID id) {
            super(id);
        }

        public static Id generate() {
            return new Id(UUID.randomUUID());
        }

        public static Id fromString(String idStr) {
            return new Id(UUID.fromString(requireLength(idStr, 36)));
        }
    }

    public final Name name;

    public Carrier(Id id, Name name) {
        super(id);
        this.name = requireNotNull(name);
    }

}
