package DDD.framework;

import static DDD.framework.EntityId.ID_IS_NULL;

public class Entity<ID> {

    protected ID id;

    public Entity(ID id) {
        if (id == null)
            throw new IllegalArgumentException(ID_IS_NULL);
        this.id = id;
    }

    public ID getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
