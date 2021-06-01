package DDD.framework;

import java.util.Objects;

public abstract class Command implements Runnable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null)
            return false;
        return getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    public abstract DomainEvents decide(Object obj);
}
