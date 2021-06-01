package DDD.framework.repository;

public interface IsPresent<ENTITY_ID> {
    boolean isPresent(ENTITY_ID id);
}
