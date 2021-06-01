package com.lb.formationtest.infrastructure.repositories;

import DDD.framework.NotYetImplementedException;
import com.lb.formationtest.domain.Carrier;
import com.lb.formationtest.domain.CarrierRepository;
import com.lb.formationtest.domain.Carriers;

import java.util.List;
import java.util.Optional;

public class StaticCarrierRepo implements CarrierRepository {

    private static Carriers carriers = Carriers.CARRIERS;

    @Override
    public Optional<Carrier> byId(Carrier.Id id) {
        throw new NotYetImplementedException();
    }

    @Override
    public void delete(Carrier wesc) {
        throw new NotYetImplementedException();
    }

    @Override
    public boolean isPresent(Carrier.Id wescId) {
        throw new NotYetImplementedException();
    }

    @Override
    public void save(Carrier wesc) {
        throw new NotYetImplementedException();
    }


    @Override
    public void deleteAll() {
        throw new NotYetImplementedException();
    }

    @Override
    public List<Carrier> listAll() {
        return carriers.toList();
    }

    @Override
    public Carriers findAll() {
        return carriers;
    }
}
