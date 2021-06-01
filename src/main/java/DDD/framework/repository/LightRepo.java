package DDD.framework.repository;

public interface LightRepo<ENTITY_ID, ENTITY> extends
        IsPresent<ENTITY_ID>,
        ById<ENTITY_ID, ENTITY>,
        Save<ENTITY>,
        Delete<ENTITY> {
}



