package com.esg.exception;

public class InternalException extends RuntimeException {

    public InternalException(String msg, Throwable t) {
        super(msg, t);
    }
}
