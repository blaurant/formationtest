package com.lb.formationtest.infrastructure.repositories;

import DDD.framework.NotYetImplementedException;
import com.lb.formationtest.domain.WESC;
import com.lb.formationtest.domain.WESCRepository;
import com.lb.formationtest.domain.WESCs;

import java.util.Optional;

import static com.lb.formationtest.domain.WESCs.onlyId;

public class StaticWESCRepo implements WESCRepository {

    private static WESCs wescs = new WESCs(WESC.WESC_1, WESC.WESC_2);

    @Override
    public Optional<WESC> byId(WESC.Id id) {
        return wescs.find(id);
    }

    @Override
    public void delete(WESC wesc) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isPresent(WESC.Id wescId) {
        return wescs.filter(onlyId(wescId)).isNotEmpty();
    }

    @Override
    public void save(WESC wesc) {
        throw new NotYetImplementedException();
    }
}
