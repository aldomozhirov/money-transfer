package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class IncompatibleCurrenciesException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public IncompatibleCurrenciesException() {
        this("Currency codes are not compatible");
    }

    public IncompatibleCurrenciesException(String string) {
        super(string, HttpStatus.BAD_REQUEST_400);
    }

}
