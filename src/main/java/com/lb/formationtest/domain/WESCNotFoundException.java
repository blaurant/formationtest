package com.lb.formationtest.domain;

public class WESCNotFoundException extends RuntimeException {
    public WESCNotFoundException() {
    }

    public WESCNotFoundException(String message) {
        super(message);
    }

    public WESCNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WESCNotFoundException(Throwable cause) {
        super(cause);
    }

    public WESCNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
