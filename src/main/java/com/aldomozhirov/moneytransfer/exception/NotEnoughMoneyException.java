package com.aldomozhirov.moneytransfer.exception;

import org.eclipse.jetty.http.HttpStatus;

public class NotEnoughMoneyException extends CheckedException {

    private static final long serialVersionUID = 1L;

    public NotEnoughMoneyException() {
        this("Not enough money error");
    }

    public NotEnoughMoneyException(String string) {
        super(string, HttpStatus.BAD_REQUEST_400);
    }

}
