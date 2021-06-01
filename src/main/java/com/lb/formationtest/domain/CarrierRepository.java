package com.lb.formationtest.domain;

import DDD.Repository;
import DDD.framework.repository.FullRepo;

@Repository(aggregate = WESC.class)
public interface CarrierRepository extends FullRepo<Carrier.Id, Carrier> {
    Carriers findAll();
}
