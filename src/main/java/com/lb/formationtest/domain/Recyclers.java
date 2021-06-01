package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Set;

public class Recyclers extends ASetOf<Recycler> {

    public static final Recyclers RECYCLERS = new Recyclers(Recycler.RECYCLER_1, Recycler.RECYCLER_2);

    public Recyclers(Set<Recycler> set) {
        super(set);
    }

    public Recyclers(Recycler... Recyclers) {
        super(Recyclers);
    }

    @Override
    public <Sub extends ASetOf<Recycler>> Sub cons(Set<Recycler> newSet) {
        return (Sub) new Recyclers(newSet);
    }

}

   