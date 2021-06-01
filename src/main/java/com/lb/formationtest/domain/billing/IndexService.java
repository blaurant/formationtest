package com.lb.formationtest.domain.billing;

@DDD.ServiceDomain
public interface IndexService {

    static Index refIndexOf(int year) {
        return new Index("163.48");
    }


    // TODO do a static map for other values
    static Index indexOf(int year, int mount) {
        return new Index("178");
    }

}
