package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class UnsupportedCurrencyException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public UnsupportedCurrencyException() {
        this("Unsupported currency code");
    }

    public UnsupportedCurrencyException(String string) {
        super(string, HttpStatus.BAD_REQUEST_400);
    }

}