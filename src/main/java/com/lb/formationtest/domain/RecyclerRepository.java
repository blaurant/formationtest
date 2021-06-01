package com.lb.formationtest.domain;

import DDD.framework.repository.FullRepo;

public interface RecyclerRepository extends FullRepo<Recycler.Id, Recycler> {
        Recyclers findAll();
}