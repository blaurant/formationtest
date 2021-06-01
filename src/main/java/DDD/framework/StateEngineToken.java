package DDD.framework;

import java.util.Objects;

public abstract class StateEngineToken {

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

    public String asJson() {
        return "command: " + this.getClass().getSimpleName();
    }
}
