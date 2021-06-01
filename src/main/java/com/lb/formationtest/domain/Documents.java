package com.lb.formationtest.domain;

import DDD.framework.ASetOf;

import java.util.Set;

public class Documents extends ASetOf<Document> {

    public Documents(Set<Document> set) {
        super(set);
    }

    public Documents(Document... Documents) {
        super(Documents);
    }

    @Override
    public <Sub extends ASetOf<Document>> Sub cons(Set<Document> newSet) {
        return (Sub) new Documents(newSet);
    }

}

   