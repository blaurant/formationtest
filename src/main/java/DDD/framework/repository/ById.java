package DDD.framework.repository;

import java.util.Optional;

public interface ById<EntityId, Entity> {
    Optional<Entity> byId(EntityId id);
}
