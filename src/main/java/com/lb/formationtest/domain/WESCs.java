package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class WESCs extends ASetOf<WESC> {

    public WESCs(Set<WESC> set) {
        super(set);
    }

    public WESCs(WESC... WESCs) {
        super(WESCs);
    }

    public static Predicate<WESC> onlyId(WESC.Id wescId) {
        return wesc -> wesc.getId().equals(wescId);
    }

    @Override
    public <Sub extends ASetOf<WESC>> Sub cons(Set<WESC> newSet) {
        return (Sub) new WESCs(newSet);
    }

    public Optional<WESC> find(WESC.Id wescId) {
        return filter(onlyId(wescId)).unique();
    }

}

   