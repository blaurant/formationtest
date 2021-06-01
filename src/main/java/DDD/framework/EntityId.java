package DDD.framework;

import static DDD.framework.Objects.requireNotNull;

public class EntityId<T> {

    public static final String ID_IS_NULL = "entity's Id is null";
    private T id;

    public EntityId(T id) {
        this.id = requireNotNull(id, "entity Id is null");
    }

    public T getId() {
        return id;
    }

    public String asString() {
        return id.toString();
    }

    @Override
    public String toString() {
        return "EntityId{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId<?> entityId = (EntityId<?>) o;
        return id.equals(entityId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
