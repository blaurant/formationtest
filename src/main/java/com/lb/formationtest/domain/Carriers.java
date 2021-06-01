package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Set;

public class Carriers extends ASetOf<Carrier> {

    public static final Carriers CARRIERS = new Carriers(Carrier.CARRIER_1, Carrier.CARRIER_2);

    public Carriers(Set<Carrier> set) {
        super(set);
    }

    public Carriers(Carrier... Carriers) {
        super(Carriers);
    }

    @Override
    public <Sub extends ASetOf<Carrier>> Sub cons(Set<Carrier> newSet) {
        return (Sub) new Carriers(newSet);
    }

}

   