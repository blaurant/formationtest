package com.lb.formationtest.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static DDD.framework.Objects.requireNotNull;
import static com.lb.formationtest.domain.Reference.Family.*;

/**
 * // TODO Bad class ! watch out for concurrency here !! ok for the poc but change that
 * use atom instead 4 example or external backend service
 */
public class Reference {

    public enum Family {
        TRANSACTION("C"), REMOVAL_REQUEST("DE"), TRANSPORT("T"), RECEPTION("R");

        private final String prefix;

        Family(String prefix) {
            this.prefix = prefix;
        }
    }

    public final Family family;
    public final Long counter;

    public Reference(Family family, Long counter) {
        this.family = requireNotNull(family);
        this.counter = requireNotNull(counter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return family == reference.family &&
                counter.equals(reference.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, counter);
    }

    public Reference incrRef() {
        return new Reference(family, counter + 1);
    }

    public String asString() {
        return family.prefix + counter;
    }

    public static synchronized Reference generateFor(Family family) {
        Reference ref = requireNotNull(refs.get(family).incrRef());
        refs.put(family, ref);
        return ref;
    }

    private static Map<Family, Reference> refs = clear();

    public static void clearAll() {
        refs = clear();
    }

    public static Map<Family, Reference> clear() {
        return new HashMap<>(Map.of(
                TRANSACTION, new Reference(TRANSACTION, 0L),
                REMOVAL_REQUEST, new Reference(REMOVAL_REQUEST, 0L),
                TRANSPORT, new Reference(TRANSPORT, 0L),
                RECEPTION, new Reference(RECEPTION, 0L)));
    }

    @Override
    public String toString() {
        return "Reference{" +
                "family=" + family +
                ", counter=" + counter +
                '}';
    }
}
