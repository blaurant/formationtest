package com.lb.formationtest.domain;

import DDD.Repository;
import DDD.framework.repository.LightRepo;

@Repository(aggregate = WESC.class)
public interface WESCRepository extends LightRepo<WESC.Id, WESC> {
}
