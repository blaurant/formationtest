package com.lb.formationtest.domain;

import DDD.framework.Entity;
import DDD.framework.EntityId;

import java.util.UUID;

import static DDD.framework.Objects.requireNotNull;
import static DDD.framework.Strings.requireLength;

@DDD.Entity(aggregate = Recycler.class)
public class Recycler extends Entity<Recycler.Id> {

    public static final Recycler.Id RECYCLER_ID_1 = Id.fromString("6a150bdb-c5f3-4409-b9c7-b3c05c09bec5");
    public static final Recycler RECYCLER_1 = new Recycler(RECYCLER_ID_1, Name.of("DENTIS"));

    public static final Recycler.Id RECYCLER_ID_2 = Id.fromString("fbcb4215-aa9c-41a5-aef0-53eec639c92f");
    public static final Recycler RECYCLER_2 = new Recycler(RECYCLER_ID_2, Name.of("FRANCE PLASTIQUES RECYCLAGE"));

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

    public Recycler(Id id, Name name) {
        super(id);
        this.name = requireNotNull(name);
    }

}
