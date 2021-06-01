package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Set;
import java.util.function.Predicate;

import static com.lb.formationtest.domain.Chain.*;

public class Chains extends ASetOf<Chain> {

    public static final Chains CHAINS = new Chains(CHAIN_1, CHAIN_2, CHAIN_3, CHAIN_4, CHAIN_5, CHAIN_6, CHAIN_7, CHAIN_8);

    public Chains(Set<Chain> set) {
        super(set);
    }

    public Chains(Chain... Chains) {
        super(Chains);
    }

    public static Predicate<Chain> onlyWESC(WESC.Id wescId) {
        return chain -> chain.wescId.equals(wescId);
    }

    @Override
    public <Sub extends ASetOf<Chain>> Sub cons(Set<Chain> newSet) {
        return (Sub) new Chains(newSet);
    }

    public Chains filterOnWESC(WESC.Id wescId) {
        return filter(onlyWESC(wescId));
    }

}

   