package com.lb.formationtest.infrastructure;

import io.vertx.core.Vertx;

public class Main {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiRestVerticle());
    }
}
