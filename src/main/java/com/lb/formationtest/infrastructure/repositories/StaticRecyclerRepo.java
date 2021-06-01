package com.lb.formationtest.infrastructure.repositories;

import DDD.framework.NotYetImplementedException;
import com.lb.formationtest.domain.Recycler;
import com.lb.formationtest.domain.RecyclerRepository;
import com.lb.formationtest.domain.Recyclers;

import java.util.List;
import java.util.Optional;

public class StaticRecyclerRepo implements RecyclerRepository {

    private static Recyclers recyclers = Recyclers.RECYCLERS;

    @Override
    public Optional<Recycler> byId(Recycler.Id id) {
        throw new NotYetImplementedException();
    }

    @Override
    public void delete(Recycler wesc) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isPresent(Recycler.Id wescId) {
        throw new NotYetImplementedException();
    }

    @Override
    public void save(Recycler wesc) {
        throw new NotYetImplementedException();
    }


    @Override
    public void deleteAll() {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Recycler> listAll() {
        return recyclers.toList();
    }

    @Override
    public Recyclers findAll() {
        return recyclers;
    }
}
