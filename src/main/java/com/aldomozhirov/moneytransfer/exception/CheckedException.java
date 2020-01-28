package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public abstract class CheckedException extends Exception {

    private int status = HttpStatus.INTERNAL_SERVER_ERROR_500;

    public CheckedException(String string) {
        super(string);
    }

    public CheckedException(String string, int status) {
        super(string);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
