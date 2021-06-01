package DDD.framework.repository;

public interface FullRepo<ENTITY_ID, ENTITY> extends
        IsPresent<ENTITY_ID>,
        ById<ENTITY_ID, ENTITY>,
        ListAll<ENTITY>,
        Save<ENTITY>,
        Delete<ENTITY>,
        DeleteAll {
}



